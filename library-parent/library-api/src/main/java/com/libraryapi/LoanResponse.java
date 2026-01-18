package com.libraryapi;

import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class LoanResponse {
    private UUID id;
    private String bookTitle;
    private String borrowerUsername;
    private Instant borrowedAt;
    private Instant returnedAt;
}