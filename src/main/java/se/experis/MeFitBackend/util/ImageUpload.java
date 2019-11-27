package se.experis.MeFitBackend.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/*
    rjanul created on 2019-11-27
*/
public final class ImageUpload {

    // prevent instantiation
    private ImageUpload() {
    }

    // check if image is valid
    public static Boolean isFileValid(MultipartFile file, Path path) throws IOException {
        byte[] bytes = file.getBytes();
        if(! new File("images/").exists() ) {
            new File("images/").mkdir();
        }
        Files.write(path, bytes);

        // check if file is an image
        Image image = ImageIO.read(new File(path.toString()));
        if (image == null) {
            // Get file to delete later
            File fileToDelete = new File(path.toString());
            fileToDelete.delete();
            return false;
        }
        return true;
    }
    // upload to firebase
    public static URL uploadToCloud(String directory, String exerciseId, Path path) throws IOException {
        // init firebase credentials
        FileInputStream serviceAccount = new FileInputStream("me-fit-49bd9-firebase-adminsdk-jh16u-0177a30c2f.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://me-fit-49bd9.firebaseio.com")
                .setStorageBucket("me-fit-49bd9.appspot.com")
                .build();

        // init firebase app
        if(FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        StorageClient storageClient = StorageClient.getInstance();
        InputStream fileToUpload = new FileInputStream(path.toString());
        // Get file extension
        String extension = "";
        int i = path.toString().lastIndexOf('.');
        if (i > 0) {
            extension = path.toString().substring(i+1);
        }
        String blobString = directory + exerciseId;
        URL urlTest = storageClient.bucket().create(blobString, fileToUpload, "image/" + extension).signUrl(9999, TimeUnit.DAYS);

        System.out.println("test url: " + urlTest);
        return urlTest;
    }
}
