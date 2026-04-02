package com.smarthome.exception;

import com.smarthome.dto.Error;
import jakarta.validation.ConstraintViolationException; // <--- Import
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException; // <--- Import
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class Globalexceptionhandler {

    //  Handle "Not Found" errors
@ExceptionHandler(Resourcenotfoundexception.class)
  public ResponseEntity<Error> handleResourceNotFound(Resourcenotfoundexception ex, WebRequest request) {
   Error error = new Error(
            LocalDateTime.now(),
             HttpStatus.NOT_FOUND.value(),
            "Not Found",
             ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
       return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //  Handle "Already Exists"
  @ExceptionHandler(Resourceexistsexception.class)
   public ResponseEntity<Error> handleResourceExists(Resourceexistsexception ex, WebRequest request) {
      Error error = new Error(
              LocalDateTime.now(),
               HttpStatus.CONFLICT.value(),
              "Data Conflict",
              ex.getMessage(),
              request.getDescription(false).replace("uri=", "")
        );
     return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    //  Handle JSON Body Validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

         return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //  Handle URL Parameter Validation Errors
        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Object> handleParamValidation(ConstraintViolationException ex) {
            Map<String, String> errors = new HashMap<>();
           ex.getConstraintViolations().forEach(violation ->
                    errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //  Handle Missing Parameters
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> handleMissingParams(MissingServletRequestParameterException ex, WebRequest request) {
     Error error = new Error(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                 "Missing Parameter",
                ex.getParameterName() + " parameter is missing",
                 request.getDescription(false).replace("uri=", "")
        );
         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // "Something went wrong" errors
    @ExceptionHandler(Exception.class)
   public ResponseEntity<Error> handleGlobalException(Exception ex, WebRequest request) {
         Error error = new Error(
                LocalDateTime.now(),
                 HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                 request.getDescription(false).replace("uri=", "")
        );
         return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}