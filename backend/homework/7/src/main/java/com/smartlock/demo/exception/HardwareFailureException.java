package com.smartlock.demo.exception;

public class HardwareFailureException extends RuntimeException {
    // I created this custom exception to handle specific hardware errors, like empty inputs.
    public HardwareFailureException(String message) {
        super(message);
    }
}