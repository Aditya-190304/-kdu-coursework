package com.smartlock.demo.model;

import lombok.Data;

@Data
public class UnlockRequest {
    // Simple object to hold the user data coming from the API
    private String user;
}