package com.smartlock.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * I created this method to catch HardwareFailureExceptions.
     * Instead of crashing the app with a stack trace,I return
     * 400 Bad Request message to the user.
     */
    @ExceptionHandler(HardwareFailureException.class)
    public ResponseEntity<String> handleHardwareError(HardwareFailureException ex) {
        // I return the specific error message so the API user knows what they did wrong
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*
     * I added this one to handle the Security Exception we added earlier.
     * If a user is blocked, they get a 403 Forbidden response.
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleSecurityError(UnauthorizedAccessException ex) {
        // Return 403 Forbidden for security blocks
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}