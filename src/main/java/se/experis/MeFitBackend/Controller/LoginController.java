package se.experis.MeFitBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.node.ObjectNode ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.experis.MeFitBackend.model.EndUser;
import se.experis.MeFitBackend.repositories.EndUserRepository;

import javax.swing.*;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class LoginController {

    @Autowired
    private final EndUserRepository endUserRepository;


    public LoginController(EndUserRepository endUserRepository) {
        this.endUserRepository = endUserRepository;
    }


    @PostMapping(value ="/login")
    public ResponseEntity validateLogin(@RequestBody ObjectNode params) {
        String email = params.get("email").asText();
        EndUser user = endUserRepository.findByEmail(email);
        System.out.println(user);
        if (user== null ) throw new  UsernameNotFoundException(email);

        if(user.getPassword().equals(params.get("password").asText())){
            return new ResponseEntity(user , HttpStatus.FOUND);
        }else{
            return new ResponseEntity("Wrong password" , HttpStatus.BAD_REQUEST);
        }
    }
}
