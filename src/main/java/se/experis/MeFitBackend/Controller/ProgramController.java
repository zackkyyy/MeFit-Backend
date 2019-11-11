package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.Profile;
import se.experis.MeFitBackend.model.Program;
import se.experis.MeFitBackend.model.ProgramWorkout;
import se.experis.MeFitBackend.repositories.ProfileRepository;
import se.experis.MeFitBackend.repositories.ProgramRepository;
import se.experis.MeFitBackend.repositories.ProgramWorkoutRepository;
import se.experis.MeFitBackend.repositories.WorkoutRepository;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class ProgramController {

    @Autowired
    private final ProgramRepository programRepository;
    private final ProfileRepository profileRepository;
    private final ProgramWorkoutRepository programWorkoutRepository;
    private final WorkoutRepository workoutRepository;

    public ProgramController(ProgramRepository programRepository, ProfileRepository profileRepository, ProgramWorkoutRepository programWorkoutRepository, WorkoutRepository workoutRepository) {
        this.programRepository = programRepository;
        this.profileRepository = profileRepository;
        this.programWorkoutRepository = programWorkoutRepository;
        this.workoutRepository = workoutRepository;
    }

    @GetMapping("/program/{ID}")
    public ResponseEntity getGoal(@PathVariable int ID) {
        return new ResponseEntity(programRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    @PostMapping("/addProgram")
    public ResponseEntity addGoal(@RequestBody ObjectNode params) {
        // name, category, profileId (who creates it), list of workoutId
        Profile profile = profileRepository.getOne(params.get("profileId").intValue());

        Program program = new Program(
            params.get("name").asText(),
            params.get("category").asText(),
            profile
        );
        programRepository.save(program);

        if(params.has("workoutId")) {
            for (int i = 0; i < params.get("workoutId").size(); i++) {
                ProgramWorkout pw = new ProgramWorkout(program, workoutRepository.findById(params.get("workoutId").get(i).intValue()).get());
                programWorkoutRepository.save(pw);
            }
        }
        return new ResponseEntity(params, HttpStatus.CREATED);
    }
}
