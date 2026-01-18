package com.libraryweb.controller;

import com.libraryservice.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final BookService bookService;

    @GetMapping("/audit")
    @Operation(summary = "Audit Book Statuses (Librarian)")
    public Map<String, Long> getAuditLogs() {
        // i call the service method that uses Streams to group and count statuses
        return bookService.getBookStatusCounts();
    }
}