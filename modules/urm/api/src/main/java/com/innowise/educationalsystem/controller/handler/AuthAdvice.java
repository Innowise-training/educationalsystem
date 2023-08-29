package com.innowise.educationalsystem.controller.handler;

import com.innowise.educationalsystem.exception.NoSuchUserException;
import com.innowise.educationalsystem.exception.UserWrongCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthAdvice {
    @ExceptionHandler({NoSuchUserException.class})
    public final ResponseEntity<String> noSuchUserCatcher(NoSuchUserException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserWrongCredentials.class})
    public final ResponseEntity<String> wrongCredentialsCatcher(UserWrongCredentials e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
