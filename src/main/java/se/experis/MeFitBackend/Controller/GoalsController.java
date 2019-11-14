package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.*;
import se.experis.MeFitBackend.repositories.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class GoalsController {

    @Value("${rootURL}")
    private URI rootURL;

    @Autowired
    private final GoalRepository goalRepository;
    private final GoalWorkoutRepository goalWorkoutRepository;
    private final ProfileRepository profileRepository;
    private final WorkoutRepository workoutRepository;
    private final ProgramGoalRepository programGoalRepository;
    private final ProgramRepository programRepository;

    public GoalsController(GoalRepository goalRepository, GoalWorkoutRepository goalWorkoutRepository, ProfileRepository profileRepository, WorkoutRepository workoutRepository, ProgramGoalRepository programGoalRepository, ProgramRepository programRepository) {
        this.goalRepository = goalRepository;
        this.goalWorkoutRepository = goalWorkoutRepository;
        this.profileRepository = profileRepository;
        this.workoutRepository = workoutRepository;
        this.programGoalRepository = programGoalRepository;
        this.programRepository = programRepository;
    }

    @GetMapping("/goal/{ID}")
    public ResponseEntity getGoal(@PathVariable int ID){
        Goal goal;
        try {
            goal = goalRepository.findById(ID).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(goal, HttpStatus.ACCEPTED);
    }

    @PostMapping("/addGoal")
    @Transactional
    public ResponseEntity addGoal(@RequestBody ObjectNode params) throws ParseException {
        // profileId, achieved, endDate // list workoutId or/and list programId
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            // check if profile exist
            if(!profileRepository.existsById(params.get("profileId").intValue())) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            Profile profile = profileRepository.getOne(params.get("profileId").intValue());

            Goal goal = new Goal(
                    params.get("achieved").asBoolean(),
                    new SimpleDateFormat("dd/MM/yyyy").parse(params.get("endDate").asText()),
                    profile
            );
            goalRepository.save(goal);

            // check if there is such element
            // connect workouts to the goal
            if (params.has("workoutId")) {
                for (int i = 0; i < params.get("workoutId").size(); i++) {
                    GoalWorkout gw = new GoalWorkout(false, goal, workoutRepository.findById(params.get("workoutId").get(i).intValue()).get());
                    goalWorkoutRepository.save(gw);
                }
            }
            // check if there is such element
            // connect programs to the goal
            if (params.has("programId")) {
                for (int i = 0; i < params.get("programId").size(); i++) {
                    ProgramGoal pg = new ProgramGoal(false, goal, programRepository.findById(params.get("programId").get(i).intValue()).get());
                    programGoalRepository.save(pg);

                    // looping through program workout list to get workout entity
                    for (int j = 0; j <pg.getProgramFk().getProgramWorkoutFk().size() ; j++) {
                        GoalWorkout gw = new GoalWorkout(false, pg, pg.getProgramFk().getProgramWorkoutFk().get(i).getWorkoutFk());
                        goalWorkoutRepository.save(gw);
                    }
                }
            }

            responseHeaders.setLocation(new URI(rootURL + "goal/" + goal.getGoalId()));

        } catch (MappingException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (URISyntaxException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    //
    @PatchMapping("goal/{ID}")
    @Transactional
    public ResponseEntity patchGoal(@PathVariable int ID, @RequestBody ObjectNode params) {
        /* example data to be received from frontend
        * achieved: "",
        * endDate: "",
        * profileId: "",
        * workouts:
        *   {
        *       workoutId: "",
        *       completed: "",
        *   },
        * programs:
        *   {
        *       programId: "",
        *       completed: "",
        *       workouts:
        *           {
        *               workoutId: "",
        *               completed: ""
        *           }
        *
        *   }
        *
        *
        * */
        try {
            Profile profile = profileRepository.findById(params.get("profileId").intValue()).get();

            Goal goal = goalRepository.findById(ID).get();
            goal.setAchieved(params.get("achieved").asBoolean());
            goal.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(params.get("endDate").asText()));
            goal.setProfileFk(profile);
            goalRepository.save(goal);

            // check if there is such element
            // update workout of the goal
            if (params.has("workouts")) {
                Workout workout = workoutRepository.findById(params.get("workouts").get("workoutId").intValue()).get();

                GoalWorkout goalW = goalWorkoutRepository.findByGoalFkAndWorkoutFkAndProgramGoalFk(goal, workout, null);
                goalW.setComplete(params.get("workouts").get("completed").asBoolean());
                goalWorkoutRepository.save(goalW);
            }

            // check if there is such element
            // update program of the goal
            if (params.has("programs")) {
                ProgramGoal programGoal = programGoalRepository.findTopByProgramFk(programRepository.findById(params.get("programs").get("programId").intValue()).get());

                programGoal.setComplete(params.get("programs").get("completed").asBoolean());
                programGoalRepository.save(programGoal);

                Workout workout = workoutRepository.findById(params.get("programs").get("workouts").get("workoutId").intValue()).get();

                GoalWorkout goalW = goalWorkoutRepository.findByProgramGoalFkAndWorkoutFk(programGoal, workout);
                goalW.setComplete(params.get("programs").get("workouts").get("completed").asBoolean());
                goalWorkoutRepository.save(goalW);
            }

        } catch (NoSuchElementException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("goal/{ID}")
    @Transactional
    public ResponseEntity deleteGoal(@PathVariable int ID) {
        try {
            goalRepository.deleteById(ID);
        } catch (NoSuchElementException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
