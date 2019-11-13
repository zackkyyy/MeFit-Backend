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
import se.experis.MeFitBackend.model.*;
import se.experis.MeFitBackend.repositories.*;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class ProfileController {

    @Autowired
    private final AddressRepository addressRepository;
    private final EndUserRepository endUserRepository;
    private final ProfileRepository profileRepository;
    private final ProgramRepository programRepository;
    private final WorkoutRepository workoutRepository;


    public ProfileController(AddressRepository addressRepository, EndUserRepository endUserRepository, ProfileRepository profileRepository, ProgramRepository programRepository, WorkoutRepository workoutRepository) {
        this.addressRepository = addressRepository;
        this.endUserRepository = endUserRepository;
        this.profileRepository = profileRepository;
        this.programRepository = programRepository;
        this.workoutRepository = workoutRepository;
    }

    @PostMapping("/createProfile")
    @Transactional
    public ResponseEntity createProfile(@RequestBody ObjectNode params){
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            // It supposed to be transaction, add all or nothing at all
            Address address = new Address(
                    params.get("street").asText(),
                    params.get("city").asText(),
                    params.get("country").asText(),
                    params.get("postalCode").intValue()
            );

            addressRepository.save(address);

            EndUser user = endUserRepository.getOne(params.get("userId").intValue());

            Profile profile = new Profile(
                    params.get("weight").intValue(),
                    params.get("height").intValue(),
                    params.get("age").intValue(),
                    params.get("fitnessLevel").asText(),
                    user,
                    address
            );
            profileRepository.save(profile);

            responseHeaders.setLocation(new URI(stuff.rootURL + "profile/" + profile.getProfileId()));
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

    @GetMapping("/profile/{ID}")
    public ResponseEntity getProfile(@PathVariable int ID){
        Profile prof;
        try {
            prof = profileRepository.findById(ID).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(prof, HttpStatus.ACCEPTED);
    }

    // TODO: check if user is updating his profile and not someone else
    @PatchMapping("/profile/{ID}")
    @Transactional
    public ResponseEntity patchProfile(@PathVariable int ID, @RequestBody ObjectNode params) {
        // height / weight / age / fitnessLevel
        // street / city / country / postalCode
        try {
            Profile prof = new Profile(
                    profileRepository.getOne(ID).getProfileId(),
                    params.get("height").intValue(),
                    params.get("weight").intValue(),
                    params.get("age").intValue(),
                    params.get("fitnessLevel").asText()
            );
            profileRepository.save(prof);

            Address addr = new Address(
                    profileRepository.getOne(ID).getAddressFk().getAddressId(),
                    params.get("street").asText(),
                    params.get("city").asText(),
                    params.get("country").asText(),
                    params.get("postalCode").intValue()
            );
            addressRepository.save(addr);
        } catch (NoSuchElementException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // TODO: user only / contributor
    // check if user is actually deleteting himself and not other user
    // check if user is contributor
    @DeleteMapping("profile/{ID}")
    @Transactional
    public ResponseEntity deleteProfile(@PathVariable int ID) {
        // Deleting a profile will delete everything connected to the user except user itself
        // address / program_goal / goal / goal_workout
        // if contributor it will change foreign keys for program / workout to null
        try {
            int addressId = profileRepository.getOne(ID).getAddressFk().getAddressId();
            // If address row is connected to only one row, delete it, otherwise do not
            if(profileRepository.findAllByAddressFk(addressId).size() == 1) {
                addressRepository.deleteById(addressId);
            }
            // if contributor, change foreign key to null for program
            List<Program> programList = programRepository.findAllByProfileFk(ID);
            for(int i = 0; i < programList.size(); i++) {
                Program prg = programRepository.getOne(programList.get(i).getProgramId());
                prg.setProfileFk(null);
                programRepository.save(prg);
            }
            // if contributor, change foreign key to null for workout
            List<Workout> workoutList = workoutRepository.findAllByProfileFk(ID);
            for(int i = 0; i < programList.size(); i++) {
                Workout wrk = workoutRepository.getOne(workoutList.get(i).getWorkoutId());
                wrk.setProfileFk(null);
                workoutRepository.save(wrk);
            }

            profileRepository.deleteById(ID);
        } catch (NoSuchElementException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
