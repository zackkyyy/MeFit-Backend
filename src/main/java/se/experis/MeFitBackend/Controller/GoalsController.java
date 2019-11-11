package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.*;
import se.experis.MeFitBackend.repositories.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        return new ResponseEntity(goalRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    @PostMapping("/addGoal")
    public ResponseEntity addGoal(@RequestBody ObjectNode params) throws ParseException {
        // profileId, achieved, endDate // list workoutId or/and list programId
        // this will need to be a transction
        System.out.println("these are the params: " + params);
        Profile profile = profileRepository.getOne(params.get("profileId").intValue());

        Goal goal = new Goal(
            params.get("achieved").asBoolean(),
            new SimpleDateFormat("dd/MM/yyyy").parse(params.get("endDate").asText()),
            profile
        );
        goalRepository.save(goal);

        if(params.has("workoutId")) {
            for (int i = 0; i < params.get("workoutId").size(); i++) {
                GoalWorkout gw = new GoalWorkout(goal, workoutRepository.findById(params.get("workoutId").get(i).intValue()).get());
                goalWorkoutRepository.save(gw);
            }
        }

        if(params.has("programId")) {
            for (int i = 0; i < params.get("programId").size(); i++) {
                ProgramGoal gw = new ProgramGoal(goal, programRepository.findById(params.get("programId").get(i).intValue()).get());
                programGoalRepository.save(gw);
            }
        }

        return new ResponseEntity(params, HttpStatus.CREATED);
    }
}
