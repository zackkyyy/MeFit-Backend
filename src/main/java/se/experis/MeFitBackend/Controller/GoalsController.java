package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import se.experis.MeFitBackend.model.Goal;
import se.experis.MeFitBackend.model.GoalWorkout;
import se.experis.MeFitBackend.model.Program;
import se.experis.MeFitBackend.model.Workout;
import se.experis.MeFitBackend.repositories.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class GoalsController {

    @Autowired
    private final GoalRepository goalRepository;
    private final GoalWorkoutRepository goalWorkoutRepository;
    private final ProfileRepository profileRepository;
    private final WorkoutRepository workoutRepository;
    private final ProgramRepository programRepository;

    public GoalsController(GoalRepository goalRepository, GoalWorkoutRepository goalWorkoutRepository, ProfileRepository profileRepository, WorkoutRepository workoutRepository, ProgramRepository programRepository) {
        this.goalRepository = goalRepository;
        this.goalWorkoutRepository = goalWorkoutRepository;
        this.profileRepository = profileRepository;
        this.workoutRepository = workoutRepository;
        this.programRepository = programRepository;
    }

    @GetMapping("/goal/{ID}")
    public ResponseEntity getGoal(@PathVariable int ID) throws JsonProcessingException {
        // get goal
        Goal goal = goalRepository.findById(ID).get();
        // get list of middle man
        List<GoalWorkout> goalWorkout = goalWorkoutRepository.findAllByGoalFk(ID);
        List<Program> program = programRepository.findAllByGoalFk(ID);


        ArrayList<Workout> workoutList = new ArrayList<Workout>();
        // loop through middleman and get workouts
        for(int i = 0; i < goal.getGoalWorkoutFk().size(); i++){
            workoutList.add(goalWorkout.get(i).getWorkoutFk());
        }

        Map<String, Object> theMap = new LinkedHashMap<>();
        theMap.put("Goal", goal);
        theMap.put("Workouts", workoutList);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        return new ResponseEntity(ow.writeValueAsString(theMap), HttpStatus.ACCEPTED);
    }
}
