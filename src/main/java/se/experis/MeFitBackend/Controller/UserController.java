package se.experis.MeFitBackend.Controller;

import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.Global.stuff;
import se.experis.MeFitBackend.model.EndUser;
import se.experis.MeFitBackend.repositories.EndUserRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

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
    public ResponseEntity addUser(@RequestBody EndUser user) {
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            EndUser usr = endUserRepository.save(user);
            responseHeaders.setLocation(new URI(stuff.rootURL + "user/" + usr.getEndUserId()));
        } catch (DuplicateKeyException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } catch (MappingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (URISyntaxException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(user, responseHeaders, HttpStatus.CREATED);
    }

    @PatchMapping("/user/{ID}")
    public ResponseEntity patchUser(@PathVariable int ID, @RequestBody EndUser endUser) {

        // User is not able to update password in this endpoint
        if(endUser.getPassword() != null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            EndUser usr = new EndUser(
                    endUserRepository.getOne(ID).getEndUserId(),
                    endUser.getFirstname(),
                    endUser.getLastname(),
                    endUser.getEmail()
            );

//            // Only admin can update users role
//            if(endUser.getRole() != 0) {
//                // check if user is admin
//                if() {
//                   usr.setRole(endUser.getRole());
//                } else {
//                    return new ResponseEntity(HttpStatus.FORBIDDEN);
//                }
//            }

            URI location = new URI(stuff.rootURL + "user/" + ID);
            responseHeaders.setLocation(location);

            endUserRepository.save(usr);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } catch (MappingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (URISyntaxException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/{ID}")
    public ResponseEntity getEndUser(@PathVariable int ID) {
        EndUser user;
        try {
            user = endUserRepository.findById(ID).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(user, HttpStatus.ACCEPTED);
    }

//    @PostMapping("/user/{ID}/update_password")
//    public ResponseEntity updatePassword(@PathVariable int ID, @RequestBody EndUser endUser) {
//        try {
//            // check if users id is the same as the one wanted to update
//            if () {
//                EndUser usr = new EndUser(
//                        endUserRepository.getOne(ID).getEndUserId(),
//                        endUser.getPassword()
//                );
//                endUserRepository.save(usr);
//            } else {
//                // if it is not the same then user does not have the rights to update different users password
//                return new ResponseEntity(HttpStatus.FORBIDDEN);
//            }
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }

    // this is the see other 303, not sure how it supposed to work
//    @GetMapping("/user/:id")
//    public ResponseEntity getAuthUser(){
//        HttpHeaders responseHeaders = new HttpHeaders();
//        int userId = 0;
//        try {
//            responseHeaders.setLocation(new URI(rootURL + "user/" + userId));
//        } catch (URISyntaxException e) {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity(HttpStatus.SEE_OTHER);
//    }

    @DeleteMapping("user/{ID}")
    @Transactional
    public ResponseEntity deleteUser(@PathVariable int ID){

        try {
            endUserRepository.deleteById(ID);
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
