package se.experis.MeFitBackend.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.experis.MeFitBackend.model.*;
import se.experis.MeFitBackend.repositories.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

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
    public ResponseEntity getProfile(@PathVariable String ID){
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
    public ResponseEntity patchProfile(@PathVariable String ID, @RequestBody ObjectNode params) {
        try {
            Profile prof = profileRepository.findById(ID).get();
            // user tries to update not his profile
            if(!prof.getUserId().equals(params.get("userId").asText())) {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }

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
            addr.setPostalCode(params.get("postalCode").asInt());
            addressRepository.save(addr);

            prof.setHeight(params.get("height").asInt());
            prof.setWeight(params.get("weight").asInt());
            prof.setAge(params.get("age").asInt());
            prof.setFitnessLevel(params.get("fitnessLevel").asText());
            prof.setAddressFk(addr);
            profileRepository.save(prof);

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
    public ResponseEntity patchProfileRole(@PathVariable String ID, @RequestBody ObjectNode params) {
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
    public ResponseEntity deleteProfile(@PathVariable String ID) {
        // if contributor it will change foreign keys for program / workout to null
        try {
            String addressId = profileRepository.findById(ID).get().getAddressFk().getAddressId();
            // If address row is connected to only one row, delete it, otherwise do not
            if(profileRepository.findAllByAddressFk(addressId).size() == 1) {
                addressRepository.deleteById(addressId);
            }
            // if contributor, change foreign key to null for program
            List<Program> programList = programRepository.findAllByProfileFk(profileRepository.findById(ID).get());
            for(int i = 0; i < programList.size(); i++) {
                Program prg = programRepository.getOne(programList.get(i).getProgramId());
                prg.setProfileFk(null);
                programRepository.save(prg);
            }
            // if contributor, change foreign key to null for workout
            List<Workout> workoutList = workoutRepository.findAllByProfileFk(profileRepository.findById(ID).get());
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

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity getFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            byte[] bytes = file.getBytes();
            if(! new File("images/").exists() ) {
                new File("images/").mkdir();
            }
            Path path = Paths.get("images/" + file.getOriginalFilename());
            Files.write(path, bytes);
            // Get file to delete later
            File fileToDelete = new File(path.toString());
            // check if file is an image
            Image image = ImageIO.read(new File(path.toString()));
            if (image == null) {
                fileToDelete.delete();
                return new ResponseEntity("Only images allowed",HttpStatus.BAD_REQUEST);
            } else {
                // upload to firebase
                FileInputStream serviceAccount = new FileInputStream("me-fit-49bd9-firebase-adminsdk-jh16u-0177a30c2f.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://me-fit-49bd9.firebaseio.com")
                        .setStorageBucket("me-fit-49bd9.appspot.com")
                        .build();
//                if(FirebaseApp.getInstance(FirebaseApp.DEFAULT_APP_NAME) == null) {
//                    FirebaseApp fireApp = FirebaseApp.initializeApp(options);
//                }

                if(FirebaseApp.getApps().isEmpty()) { //<--- check with this line
                    FirebaseApp.initializeApp(options);
                }
                FirebaseDatabase databas =  FirebaseDatabase.getInstance();
                DatabaseReference ref = databas.getReference();
                ref.setValueAsync("Million reasons");

                System.out.println("getkey: " + ref.push().getKey());
                System.out.println(ref.push().setValueAsync("million reeasonss !!").get());
                System.out.println("getkey: " + ref.push().getKey());


                StorageClient storageClient = StorageClient.getInstance();
                InputStream testFile = new FileInputStream(path.toString());
                String extension = "";

                int i = path.toString().lastIndexOf('.');
                if (i > 0) {
                    extension = path.toString().substring(i+1);
                }

                System.out.println("ext: " + extension);
                String blobString = "images/exercise/" + "muahazxc";
                URL urlTest = storageClient.bucket().create(blobString, testFile, "image/" + extension).signUrl(9999, TimeUnit.DAYS);

                System.out.println("test url: " + urlTest);
            }

            // delete image at the end
            fileToDelete.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
