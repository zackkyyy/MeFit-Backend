package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.Address;
import se.experis.MeFitBackend.model.EndUser;
import se.experis.MeFitBackend.model.Profile;
import se.experis.MeFitBackend.repositories.AddressRepository;
import se.experis.MeFitBackend.repositories.EndUserRepository;
import se.experis.MeFitBackend.repositories.ProfileRepository;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class ProfileController {

    @Autowired
    private final AddressRepository addressRepository;
    private final EndUserRepository endUserRepository;
    private final ProfileRepository profileRepository;

    public ProfileController(AddressRepository addressRepository, EndUserRepository endUserRepository, ProfileRepository profileRepository) {
        this.addressRepository = addressRepository;
        this.endUserRepository = endUserRepository;
        this.profileRepository = profileRepository;
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
}
