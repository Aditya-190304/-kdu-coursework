package com.libraryapi;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class ErrorResponse {
    private Instant timestamp;
    private String errorCode;
    private String message;
    private String correlationId;
}