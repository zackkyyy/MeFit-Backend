package se.experis.MeFitBackend.Controller;

import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import se.experis.MeFitBackend.Global.stuff;
import se.experis.MeFitBackend.model.Exercise;
import se.experis.MeFitBackend.model.Set;
import se.experis.MeFitBackend.repositories.ExerciseRepository;
import se.experis.MeFitBackend.repositories.SetRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class ExerciseController {

    @Autowired
    private final ExerciseRepository exerciseRepository;
    private final SetRepository setRepository;

    public ExerciseController(ExerciseRepository exerciseRepository, SetRepository setRepository) {
        this.exerciseRepository = exerciseRepository;
        this.setRepository = setRepository;
    }

    // TODO: Contributor only
    @PostMapping("/addExercise")
    public ResponseEntity addExercise(@RequestBody Exercise exercise){
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            Exercise ex = exerciseRepository.save(exercise);

            responseHeaders.setLocation(new URI(stuff.rootURL + "exercise/" + ex.getExerciseId()));
        } catch (MappingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (URISyntaxException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/exercises/{ID}")
    public ResponseEntity getExercise(@PathVariable int ID){
        Exercise ex;
        try {
            ex = exerciseRepository.findById(ID).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(ex, HttpStatus.ACCEPTED);
    }

    // Returns a list of currently available exercises arranged alphabetically by Target muscle
    @GetMapping("/exercises")
    public ResponseEntity getExerciseList(){
        List<Exercise> ex;
        try {
            ex = exerciseRepository.findAllByOrderByTargetMuscleAsc();
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(ex, HttpStatus.ACCEPTED);
    }

    // TODO: Contributor only
    @PatchMapping("/exercises/{ID}")
    public ResponseEntity patchExercise(@PathVariable int ID, @RequestBody Exercise exercise) {
        try {
            // check if exercise exist
            if(!exerciseRepository.existsById(ID)) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            Exercise ex = exerciseRepository.getOne(ID);
            ex.setName(exercise.getName());
            ex.setDescription(exercise.getDescription());
            ex.setTargetMuscle(exercise.getTargetMuscle());
            ex.setImageLink(exercise.getImageLink());
            ex.setVideoLink(exercise.getVideoLink());

            exerciseRepository.save(ex);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // TODO: Contributor only
    @DeleteMapping("/exercises/{ID}")
    @Transactional
    public ResponseEntity deleteExercise(@PathVariable int ID) {

        try {
            exerciseRepository.deleteById(ID);
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

    @PostMapping("/addSet")
    public ResponseEntity addSet(@RequestBody Set set) {
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            Set ss = setRepository.save(set);

            URI location = new URI(stuff.rootURL + "set/" + ss.getSetId());
            responseHeaders.setLocation(location);
        } catch (MappingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (URISyntaxException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(set, HttpStatus.CREATED);
    }

    @GetMapping("/set/{ID}")
    public ResponseEntity getSet(@PathVariable int ID){
        Set set;
        try {
            set = setRepository.findById(ID).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(set, HttpStatus.ACCEPTED);
    }
}
