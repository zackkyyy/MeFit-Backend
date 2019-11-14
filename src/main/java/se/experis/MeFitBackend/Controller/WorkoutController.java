package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.Global.stuff;
import se.experis.MeFitBackend.model.Profile;
import se.experis.MeFitBackend.model.Set;
import se.experis.MeFitBackend.model.Workout;
import se.experis.MeFitBackend.repositories.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class WorkoutController {

    @Autowired
    private final WorkoutRepository workoutRepository;
    private final SetRepository setRepository;
    private final ProfileRepository profileRepository;
    private final ExerciseRepository exerciseRepository;
    private final ProgramWorkoutRepository programWorkoutRepository;
    private final GoalWorkoutRepository goalWorkoutRepository;

    public WorkoutController(WorkoutRepository workoutRepository, SetRepository setRepository, ProfileRepository profileRepository, ExerciseRepository exerciseRepository, ProgramWorkoutRepository programWorkoutRepository, GoalWorkoutRepository goalWorkoutRepository) {
        this.workoutRepository = workoutRepository;
        this.setRepository = setRepository;
        this.profileRepository = profileRepository;
        this.exerciseRepository = exerciseRepository;
        this.programWorkoutRepository = programWorkoutRepository;
        this.goalWorkoutRepository = goalWorkoutRepository;
    }

    @GetMapping("/workout/{ID}")
    public ResponseEntity getWorkout(@PathVariable int ID){
        Workout workout;
        try {
            workout = workoutRepository.findById(ID).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(workout, HttpStatus.ACCEPTED);
    }

    // TODO: Contributor only
    // set profile from params
    @PostMapping(value = "/addWorkout")
    @Transactional
    public ResponseEntity addWorkout(@RequestBody ObjectNode params){
        HttpHeaders responseHeaders = new HttpHeaders();
        Workout wrk;
        try {
           // Profile profile = profileRepository.getOne(params.get("profileId").intValue());
            wrk = workoutRepository.save(new Workout(
                    params.get("name").asText(),
                    params.get("type").asText(),
                    profileRepository.getOne(1)
            ));
            workoutRepository.save(wrk);

            // loop through set (exercise) list
            for (int i = 0; i < params.get("exercises").size(); i++) {
                setRepository.save(new Set(
                        params.get("exercises").get(i).get("reps").intValue(),
                        params.get("exercises").get(i).get("sets").intValue(),
                        exerciseRepository.getOne(params.get("exercises").get(i).get("exerciseId").intValue()),
                        wrk
                ));
            }

            responseHeaders.setLocation(new URI(stuff.rootURL + "workout/" + wrk.getWorkoutId()));

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

    // TODO: Contributor only
    @PatchMapping("workout/{ID}")
    @Transactional
    public ResponseEntity patchWorkout(@PathVariable int ID, @RequestBody ObjectNode params) {
        try {
            // check if there is a conflict (users using it)
            if(programWorkoutRepository.findTopByWorkoutFk(workoutRepository.findById(ID).get()) != null ||
                    goalWorkoutRepository.findTopByWorkoutFk(workoutRepository.findById(ID).get()) != null) {
                return new ResponseEntity(HttpStatus.CONFLICT);
            } else {

                // delete all sets associated to workout
                setRepository.deleteByWorkoutFk(workoutRepository.findById(ID).get());

                Profile profile = profileRepository.findById(params.get("profileId").intValue()).get();

                Workout wrk = workoutRepository.getOne(ID);
                wrk.setName(params.get("name").asText());
                wrk.setType(params.get("type").asText());
                wrk.setProfileFk(profile);
                workoutRepository.save(wrk);

                // loop through set (exercise) list
                for (int i = 0; i < params.get("exercises").size(); i++) {
                    setRepository.save(new Set(
                            params.get("exercises").get(i).get("reps").intValue(),
                            params.get("exercises").get(i).get("sets").intValue(),
                            exerciseRepository.getOne(params.get("exercises").get(i).get("exerciseId").intValue()),
                            wrk
                    ));
                }
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

    // TODO: Contributor only
    @DeleteMapping("workout/{ID}")
    @Transactional
    public ResponseEntity deleteWorkout(@PathVariable int ID) {
        try {
            // check if there is a conflict (users using it)
            if(programWorkoutRepository.findTopByWorkoutFk(workoutRepository.findById(ID).get()) == null &&
                    goalWorkoutRepository.findTopByWorkoutFk(workoutRepository.findById(ID).get()) == null) {
                workoutRepository.deleteById(ID);
            } else {
                return new ResponseEntity(HttpStatus.CONFLICT);
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

}
