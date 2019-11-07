package se.experis.MeFitBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.Exercise;
import se.experis.MeFitBackend.model.Set;
import se.experis.MeFitBackend.repositories.ExerciseRepository;
import se.experis.MeFitBackend.repositories.SetRepository;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class ExerciseController {

    @Autowired
    private final ExerciseRepository exerciseRepository;
    private final SetRepository setRepository;

    public ExerciseController(ExerciseRepository exerciseRepository, SetRepository setRepository) {
        this.exerciseRepository = exerciseRepository;
        this.setRepository = setRepository;
    }

    @PostMapping("/addExercise")
    public ResponseEntity addExercise(@RequestBody Exercise exercise){
        exerciseRepository.save(exercise);
        return new ResponseEntity(exercise, HttpStatus.CREATED);
    }

    @GetMapping("/exercise/{ID}")
    public ResponseEntity getExercise(@PathVariable int ID){
        return new ResponseEntity(exerciseRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    @PostMapping("/addSet")
    public ResponseEntity addSet(@RequestBody Set set){
        System.out.println("this is set: " + set.toString());

        setRepository.save(set);
        return new ResponseEntity(set, HttpStatus.CREATED);
    }

    @GetMapping("/set/{ID}")
    public ResponseEntity getSet(@PathVariable int ID){
        return new ResponseEntity(setRepository.findById(ID), HttpStatus.ACCEPTED);
    }
}
