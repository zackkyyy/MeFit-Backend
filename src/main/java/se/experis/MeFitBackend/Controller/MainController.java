package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
    private final WorkoutRepository workoutRepository;
    private final AddressRepository addressRepository;
    private final GoalRepository goalRepository;
    private final GoalWorkoutRepository goalWorkoutRepository;
    private final ProgramRepository programRepository;
    private final ProgramWorkoutRepository programWorkoutRepository;
    private final ProfileGoalRepository profileGoalRepository;
    private final ProgramProfileRepository programProfileRepository;

    public MainController(EndUserRepository endUserRepository, ProfileRepository profileRepository, ProfileWorkoutRepository profileWorkoutRepository, ExerciseRepository exerciseRepository, SetRepository setRepository, WorkoutRepository workoutRepository, AddressRepository addressRepository, GoalRepository goalRepository, GoalWorkoutRepository goalWorkoutRepository, ProgramRepository programRepository, ProgramWorkoutRepository programWorkoutRepository, ProfileGoalRepository profileGoalRepository, ProgramProfileRepository programProfileRepository) {
        this.endUserRepository = endUserRepository;
        this.profileRepository = profileRepository;
        this.profileWorkoutRepository = profileWorkoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.setRepository = setRepository;
        this.workoutRepository = workoutRepository;
        this.addressRepository = addressRepository;
        this.goalRepository = goalRepository;
        this.goalWorkoutRepository = goalWorkoutRepository;
        this.programRepository = programRepository;
        this.programWorkoutRepository = programWorkoutRepository;
        this.profileGoalRepository = profileGoalRepository;
        this.programProfileRepository = programProfileRepository;
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody EndUser user){
        endUserRepository.save(user);
     return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @GetMapping("/user/{ID}")
    public ResponseEntity getEndUser(@PathVariable int ID){
        return new ResponseEntity(endUserRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/createProfile")
    public ResponseEntity createProfile(@RequestBody ObjectNode params){
        // It supposed to be transaction, add all or nothing at all
        Address address = new Address(
                params.get("street").asText(),
                params.get("city").asText(),                                
                params.get("country").asText(),
                params.get("postalCode").intValue()
        );

        addressRepository.save(address);

        EndUser user = endUserRepository.findById(params.get("userId").intValue()).get();

        Profile profile = new Profile(
                params.get("weight").intValue(),
                params.get("height").intValue(),
                params.get("age").intValue(),
                params.get("fitnessLevel").asText(),
                user,
                address
        );

        profileRepository.save(profile);
        return new ResponseEntity(profile, HttpStatus.CREATED);
    }

    @GetMapping("/profile/{ID}")
    public ResponseEntity getProfile(@PathVariable int ID){
        return new ResponseEntity(profileRepository.findById(ID), HttpStatus.ACCEPTED);
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

    @PostMapping("/addWorkout")
    public ResponseEntity addWorkout(@RequestBody Workout workout){
        workoutRepository.save(workout);
        return new ResponseEntity(workout, HttpStatus.CREATED);
    }

    @GetMapping("/set/{ID}")
    public ResponseEntity getSet(@PathVariable int ID){
        return new ResponseEntity(setRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    @GetMapping("/workout/{ID}")
    public ResponseEntity getWorkout(@PathVariable int ID){
        return new ResponseEntity(workoutRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    @PostMapping("/addProfileWorkout")
    public ResponseEntity addProfileWorkout(@RequestBody ObjectNode params){
        System.out.println("this is shit: " + params);

        Workout workout = workoutRepository.findById(params.get("workoutFk").intValue()).get();
        Profile profile = profileRepository.findById(params.get("profileFk").intValue()).get();

        ProfileWorkout pw = profileWorkoutRepository.save(new ProfileWorkout(profile, workout));
        return new ResponseEntity(pw, HttpStatus.CREATED);
    }


}
