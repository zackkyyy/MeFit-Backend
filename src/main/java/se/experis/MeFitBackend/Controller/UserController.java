package se.experis.MeFitBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.model.EndUser;
import se.experis.MeFitBackend.repositories.EndUserRepository;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class UserController {

    @Autowired
    private final EndUserRepository endUserRepository;

    public UserController(EndUserRepository endUserRepository) {
        this.endUserRepository = endUserRepository;
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

}
