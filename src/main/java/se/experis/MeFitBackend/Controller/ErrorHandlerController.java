package se.experis.MeFitBackend.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartException;

import java.net.URISyntaxException;
import java.util.NoSuchElementException;

/*
    rjanul created on 2019-11-08
*/
@ControllerAdvice
public class ErrorHandlerController implements ErrorController{

    @RequestMapping("/error")
    public ResponseEntity handleError() {
        return new ResponseEntity("Not Found", HttpStatus.NOT_FOUND);
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }

    // for image upload
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleMultipartException() {
        return new ResponseEntity("Image is to large", HttpStatus.BAD_REQUEST);
    }
}
