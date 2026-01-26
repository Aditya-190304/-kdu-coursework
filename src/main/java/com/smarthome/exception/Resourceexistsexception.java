package com.smarthome.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This tells Spring: "If this error happens, return 409 CONFLICT"
@ResponseStatus(value = HttpStatus.CONFLICT)
public class Resourceexistsexception extends RuntimeException {
    public Resourceexistsexception(String message) {
    super(message);
    }
}