package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
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

    @Value("${rootURL}")
    private URI rootURL;

    @Autowired
    private final AddressRepository addressRepository;
    private final ProfileRepository profileRepository;
    private final ProgramRepository programRepository;
    private final WorkoutRepository workoutRepository;


    public ProfileController(AddressRepository addressRepository, ProfileRepository profileRepository, ProgramRepository programRepository, WorkoutRepository workoutRepository) {
        this.addressRepository = addressRepository;
        this.profileRepository = profileRepository;
        this.programRepository = programRepository;
        this.workoutRepository = workoutRepository;
    }

    @PostMapping("/createProfile")
    @Transactional
    public ResponseEntity createProfile(@RequestBody ObjectNode params){
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            Profile profile = new Profile();
            profile.setUserId(params.get("userId").asText());
            profile.setRole(1);
            profileRepository.save(profile);

            responseHeaders.setLocation(new URI(rootURL + "profile/" + profile.getProfileId()));
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

    @GetMapping("/profile/user/{ID}")
    public ResponseEntity getUserProfile(@PathVariable String ID){
        Profile prof;
        try {
            prof = profileRepository.findByUserId(ID);
            if(prof == null) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(prof, HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile")
    public ResponseEntity getAllProfiles(){
        List<Profile> prof;
        try {
            prof = profileRepository.findAll();

        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(prof, HttpStatus.ACCEPTED);
    }

    // TODO: check if user is updating his profile and not someone else
    @PatchMapping("/profile/{ID}")
    @Transactional
    public ResponseEntity patchProfile(@PathVariable int ID, @RequestBody ObjectNode params) {
        try {
            Profile prof = profileRepository.findById(ID).get();
            // user tries to update not his profile
            if(prof.getUserId().equals(params.get("userId").asText())) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            prof.setHeight(params.get("height").asInt());
            prof.setWeight(params.get("weight").asInt());
            prof.setAge(params.get("age").intValue());
            prof.setFitnessLevel(params.get("fitnessLevel").asText());
            profileRepository.save(prof);

            Address addr;
            // address row, so retrieve it
            if(prof.getAddressFk() != null) {
                addr = addressRepository.findById(prof.getAddressFk().getAddressId()).get();
            }
            // address does not exist, create new
            else {
                addr = new Address();
            }
            // set address details
            addr.setStreet(params.get("street").asText());
            addr.setCity(params.get("city").asText());
            addr.setCountry(params.get("country").asText());
            addr.setPostalCode(params.get("postalCode").intValue());
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

    // TODO: only admin
    // testing
    @PatchMapping("/profile/role/user/{ID}")
    public ResponseEntity patchProfileRole(@PathVariable int ID, @RequestBody ObjectNode params) {
        try {
            if(params.get("role").asInt() > 3) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } else if (params.has("role")) {
                Profile prof = profileRepository.findById(ID).get();
                prof.setRole(params.get("role").asInt());
                profileRepository.save(prof);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
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
        // if contributor it will change foreign keys for program / workout to null
        try {
            int addressId = profileRepository.findById(ID).get().getAddressFk().getAddressId();
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
