package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.Address;
import se.experis.MeFitBackend.model.EndUser;
import se.experis.MeFitBackend.model.Profile;
import se.experis.MeFitBackend.repositories.AddressRepository;
import se.experis.MeFitBackend.repositories.EndUserRepository;
import se.experis.MeFitBackend.repositories.ProfileRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class ProfileController {
    private final String rootURL = "http://localhost:8080/";

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
        return new ResponseEntity(profileRepository.findById(ID), HttpStatus.ACCEPTED);
    }
}
