package com.smartlock.demo.exception;

public class UnauthorizedAccessException extends RuntimeException {
    // I created this specific exception so I can easily distinguish
    // between a "bad user" (this) and a "system crash" (other errors).
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}