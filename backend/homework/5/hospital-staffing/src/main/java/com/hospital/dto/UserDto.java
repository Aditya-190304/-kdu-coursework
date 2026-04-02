package com.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record UserDto(
        UUID id,
        @NotBlank(message = "Username is required") String username,
        String timezone,
        UUID tenantId
) {}