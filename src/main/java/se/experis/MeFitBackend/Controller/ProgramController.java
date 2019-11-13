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
import se.experis.MeFitBackend.model.Profile;
import se.experis.MeFitBackend.model.Program;
import se.experis.MeFitBackend.model.ProgramWorkout;
import se.experis.MeFitBackend.repositories.ProfileRepository;
import se.experis.MeFitBackend.repositories.ProgramRepository;
import se.experis.MeFitBackend.repositories.ProgramWorkoutRepository;
import se.experis.MeFitBackend.repositories.WorkoutRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;

/*
    rjanul created on 2019-11-07
*/
@RestController
public class ProgramController {

    @Autowired
    private final ProgramRepository programRepository;
    private final ProfileRepository profileRepository;
    private final ProgramWorkoutRepository programWorkoutRepository;
    private final WorkoutRepository workoutRepository;

    public ProgramController(ProgramRepository programRepository, ProfileRepository profileRepository, ProgramWorkoutRepository programWorkoutRepository, WorkoutRepository workoutRepository) {
        this.programRepository = programRepository;
        this.profileRepository = profileRepository;
        this.programWorkoutRepository = programWorkoutRepository;
        this.workoutRepository = workoutRepository;
    }

    @GetMapping("/program/{ID}")
    public ResponseEntity getGoal(@PathVariable int ID) {
        return new ResponseEntity(programRepository.findById(ID), HttpStatus.ACCEPTED);
    }

    // TODO: Contributor only
    @PostMapping("/addProgram")
    @Transactional
    public ResponseEntity addGoal(@RequestBody ObjectNode params) {
        // name, category, profileId (who creates it), list of workoutId
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            Profile profile = profileRepository.getOne(params.get("profileId").intValue());

            Program program = new Program(
                    params.get("name").asText(),
                    params.get("category").asText(),
                    profile
            );
            programRepository.save(program);

            // connect program to workouts by making connection at programWorkout table
            if (params.has("workoutId")) {
                for (int i = 0; i < params.get("workoutId").size(); i++) {
                    ProgramWorkout pw = new ProgramWorkout(program, workoutRepository.findById(params.get("workoutId").get(i).intValue()).get());
                    programWorkoutRepository.save(pw);
                }
            }

            responseHeaders.setLocation(new URI(stuff.rootURL + "program/" + program.getProgramId()));

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
}
