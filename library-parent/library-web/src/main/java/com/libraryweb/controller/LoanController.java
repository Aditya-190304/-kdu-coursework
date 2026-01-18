package com.libraryweb.controller;

import com.libraryapi.LoanResponse;
import com.libraryservice.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/{bookId}/borrow")
    @ResponseStatus(HttpStatus.CREATED) // i return 201 created to stick to standard rest conventions
    @Operation(summary = "Borrow Book (Member)")
    public LoanResponse borrow(@PathVariable UUID bookId, @AuthenticationPrincipal UserDetails user) {
        // i extract the username directly from the security context to ensure the user is who they say they are
        return loanService.borrowBook(bookId, user.getUsername());
    }

    @PostMapping("/{bookId}/return")
    @Operation(summary = "Return Book (Member)")
    public void returnBook(@PathVariable UUID bookId, @AuthenticationPrincipal UserDetails user) {
        // i pass the username down to the service so it can verify that this specific user actually borrowed the book
        loanService.returnBook(bookId, user.getUsername());
    }
}