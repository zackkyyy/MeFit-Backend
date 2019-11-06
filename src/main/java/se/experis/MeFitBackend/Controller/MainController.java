package se.experis.MeFitBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.*;
import se.experis.MeFitBackend.repositories.*;

/**
 * Author : Zacky Kharboutli
 * Date : 2019-11-06
 * Project: MeFit-Backend
 */
@RestController
public class MainController {

    @Autowired
    private final EndUserRepository endUserRepository;
    private final ProfileRepository profileRepository;
    private final ProfileWorkoutRepository profileWorkoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final SetRepository setRepository;
    private WorkoutRepository workoutRepository;

    public MainController( EndUserRepository endUserRepository, ProfileRepository profileRepository, ProfileWorkoutRepository profileWorkoutRepository, ExerciseRepository exerciseRepository, SetRepository setRepository, WorkoutRepository workoutRepository) {
        this.endUserRepository = endUserRepository;
        this.profileRepository = profileRepository;
        this.profileWorkoutRepository = profileWorkoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.setRepository = setRepository;
        this.workoutRepository = workoutRepository;
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody EndUser user){
        endUserRepository.save(user);
     return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PostMapping("/createProfile")
    public ResponseEntity createProfile(@RequestBody Profile profile){
        profileRepository.save(profile);
        return new ResponseEntity(profile, HttpStatus.CREATED);
    }


    @GetMapping("/username")
    public ResponseEntity getUsername(){
        EndUser user = profileRepository.findById(2).get().getEndUserFk();
        System.out.println("profile : " +  profileRepository.findById(2).get() );
        System.out.println(user);
        return new ResponseEntity(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/{ID}")
    public ResponseEntity getEndUser(@PathVariable int ID){
        return new ResponseEntity(profileRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    @PostMapping("/addExercise")
    public ResponseEntity addExercise(@RequestBody Exercise exercise){
        exerciseRepository.save(exercise);
        return new ResponseEntity(exercise, HttpStatus.CREATED);
    }

    @PostMapping("/addSet")
    public ResponseEntity addSet(@RequestBody Set set){
        setRepository.save(set);
        return new ResponseEntity(set, HttpStatus.CREATED);
    }

    @PostMapping("/addWorkout")
    public ResponseEntity addWorkout(@RequestBody Workout workout){
        workoutRepository.save(workout);
        return new ResponseEntity(workout, HttpStatus.CREATED);
    }

    @PostMapping("/addProfileWorkout")
    public ResponseEntity addProfileWorkout(@RequestBody ProfileWorkout profileWorkout){
        profileWorkoutRepository.save(profileWorkout);
        return new ResponseEntity(profileWorkout, HttpStatus.CREATED);
    }

    @GetMapping("/exercise/{ID}")
    public ResponseEntity getExercise(@PathVariable int ID){
        return new ResponseEntity(exerciseRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    @GetMapping("/set/{ID}")
    public ResponseEntity getSet(@PathVariable int ID){
        return new ResponseEntity(setRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    @GetMapping("/workout/{ID}")
    public ResponseEntity getWorkout(@PathVariable int ID){
        return new ResponseEntity(workoutRepository.findById(ID), HttpStatus.ACCEPTED);
    }
}
