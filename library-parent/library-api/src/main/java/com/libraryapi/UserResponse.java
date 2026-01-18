package com.libraryapi;

import lombok.Data;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String username;
    private String role;

    // i did this to ensure we don't accidentally expose the password hash to the frontend
}