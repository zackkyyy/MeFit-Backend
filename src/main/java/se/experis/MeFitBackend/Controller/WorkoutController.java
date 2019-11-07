package se.experis.MeFitBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.Workout;
import se.experis.MeFitBackend.repositories.WorkoutRepository;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class WorkoutController {

    @Autowired
    private final WorkoutRepository workoutRepository;

    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @PostMapping(value = "/addWorkout")
    public ResponseEntity addWorkout(@RequestBody Workout workout){
        workoutRepository.save(workout);
        return new ResponseEntity(workout, HttpStatus.CREATED);
    }


    @GetMapping("/workout/{ID}")
    public ResponseEntity getWorkout(@PathVariable int ID){
        return new ResponseEntity(workoutRepository.findById(ID), HttpStatus.ACCEPTED);
    }

}
