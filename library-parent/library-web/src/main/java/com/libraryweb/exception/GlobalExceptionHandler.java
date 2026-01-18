package com.libraryweb.exception;

import com.libraryapi.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice // i use this centralized advice to handle exceptions from all controllers uniformly
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(IllegalStateException ex) {
        // i map business logic violations (like invalid state transitions) to a 409 conflict
        log.warn("Conflict: {}", ex.getMessage());
        return buildError("CONFLICT", ex.getMessage());
    }

    // i specifically handle optimistic locking failures here to catch race conditions
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOptimisticLock() {
        log.error("Optimistic lock failure");
        return buildError("CONCURRENT_MODIFICATION", "Resource modified by another user.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        // i collect all field validation errors into a single comma-separated string for the client
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + " " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildError("VALIDATION_ERROR", msg);
    }

    private ErrorResponse buildError(String code, String msg) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .errorCode(code)
                .message(msg)
                // i include the correlation ID from the logging context (MDC) so i can trace this error in the logs
                .correlationId(MDC.get("correlationId"))
                .build();
    }
}