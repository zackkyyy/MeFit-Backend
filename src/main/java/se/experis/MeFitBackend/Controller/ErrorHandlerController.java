package se.experis.MeFitBackend.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    rjanul created on 2019-11-08
*/
@RestController
public class ErrorHandlerController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity handleError() {
        return new ResponseEntity("Not Found", HttpStatus.NOT_FOUND);
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
