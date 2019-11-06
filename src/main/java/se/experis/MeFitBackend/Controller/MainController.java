package se.experis.MeFitBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.EndUser;
import se.experis.MeFitBackend.model.Profile;
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
}
