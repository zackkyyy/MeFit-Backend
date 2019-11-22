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
import se.experis.MeFitBackend.model.Profile;
import se.experis.MeFitBackend.model.Program;
import se.experis.MeFitBackend.model.ProgramWorkout;
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
public class ProgramController {

    @Value("${rootURL}")
    private URI rootURL;

    @Autowired
    private final ProgramRepository programRepository;
    private final ProfileRepository profileRepository;
    private final ProgramWorkoutRepository programWorkoutRepository;
    private final WorkoutRepository workoutRepository;
    private final ProgramGoalRepository programGoalRepository;

    public ProgramController(ProgramRepository programRepository, ProfileRepository profileRepository, ProgramWorkoutRepository programWorkoutRepository, WorkoutRepository workoutRepository, ProgramGoalRepository programGoalRepository) {
        this.programRepository = programRepository;
        this.profileRepository = profileRepository;
        this.programWorkoutRepository = programWorkoutRepository;
        this.workoutRepository = workoutRepository;
        this.programGoalRepository = programGoalRepository;
    }

    @GetMapping("/program/{ID}")
    public ResponseEntity getProgram(@PathVariable int ID) {
        Program program;
        try {
            program = programRepository.findById(ID).get();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(program, HttpStatus.ACCEPTED);
    }

    @GetMapping("/program")
    public ResponseEntity getProgramList() {
        List<Program> program;
        try {
            program = programRepository.findAll();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(program, HttpStatus.ACCEPTED);
    }

    // returns user's program list
    @GetMapping("/program/user/{ID}")
    public ResponseEntity getUserProgramList(@PathVariable int ID) {
        List<Program> program;
        try {
            program = programRepository.findAllByProfileFk(ID);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(program, HttpStatus.ACCEPTED);
    }

    // TODO: Contributor only
    @PostMapping("/addProgram")
    @Transactional
    public ResponseEntity addProgram(@RequestBody ObjectNode params) {
        // name, category, profileId (who creates it), list of workoutId
        HttpHeaders responseHeaders = new HttpHeaders();

        try {
            Profile profile = profileRepository.findById(params.get("profileId").intValue()).get();

            Program program = new Program(
                    params.get("name").asText(),
                    params.get("category").asText(),
                    profile
            );
            programRepository.save(program);

            // connect program to workouts by making connection at programWorkout table
            if (params.has("workoutList")) {
                for (int i = 0; i < params.get("workoutList").size(); i++) {
                    ProgramWorkout pw = new ProgramWorkout(program, workoutRepository.findById(params.get("workoutList").get(i).get("workoutId").intValue()).get());
                    programWorkoutRepository.save(pw);
                }
            }

            responseHeaders.setLocation(new URI(rootURL + "program/" + program.getProgramId()));

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

    // TODO: Contributor only
    @PatchMapping("program/{ID}")
    @Transactional
    public ResponseEntity patchProgram(@PathVariable int ID, @RequestBody ObjectNode params) {
        try {
            // check if there is a conflict (there are users using it)
            if(programGoalRepository.findTopByProgramFk(programRepository.findById(ID).get()) != null) {
                return new ResponseEntity(HttpStatus.CONFLICT);
            } else {
                // delete existing workouts
                programWorkoutRepository.deleteByProgramFk(programRepository.findById(ID).get());

                Profile profile = profileRepository.findById(params.get("profileId").intValue()).get();

                Program program = programRepository.findById(ID).get();
                program.setName(params.get("name").asText());
                program.setCategory(params.get("category").asText());
                program.setProfileFk(profile);
                programRepository.save(program);

                // connect program to workouts by making connection at programWorkout table
                if (params.has("workoutId")) {
                    for (int i = 0; i < params.get("workoutId").size(); i++) {
                        ProgramWorkout pw = new ProgramWorkout(program, workoutRepository.findById(params.get("workoutId").get(i).intValue()).get());
                        programWorkoutRepository.save(pw);
                    }
                }
            }
        } catch (NoSuchElementException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // TODO: Contributor only
    @DeleteMapping("program/{ID}")
    @Transactional
    public ResponseEntity deleteProgram(@PathVariable int ID) {
        try {
            // check if program is connected to any goals if no delete
            if(programGoalRepository.findTopByProgramFk(programRepository.findById(ID).get()) == null) {
                programRepository.deleteById(ID);
            } else {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
        } catch (NoSuchElementException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
