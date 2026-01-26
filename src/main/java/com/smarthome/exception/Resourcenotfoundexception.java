package com.smarthome.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This tells Spring: "When this error happens, send a 404 NOT FOUND status"
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class Resourcenotfoundexception extends RuntimeException {
public Resourcenotfoundexception(String message) {
        super(message);
    }
}