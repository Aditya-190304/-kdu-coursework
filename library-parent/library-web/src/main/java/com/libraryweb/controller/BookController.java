package com.libraryweb.controller;

import com.libraryapi.*;
import com.libraryservice.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor // i use this to automatically inject the final fields (dependency injection)
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // i explicitly return 201 Created instead of the default 200 OK
    @Operation(summary = "Create Book (Librarian)")
    public BookResponse create(@RequestBody @Valid CreateBookRequest req) {
        // i added @Valid here to trigger the bean validation constraints defined in the DTO
        return bookService.create(req);
    }

    @PatchMapping("/{id}/catalog")
    @Operation(summary = "Catalog Book (Librarian)")
    public BookResponse catalog(@PathVariable UUID id) {
        // i map this to PATCH because i am updating the state of an existing resource, not replacing it
        return bookService.catalog(id);
    }

    @GetMapping
    @Operation(summary = "Search Books")
    public Page<BookResponse> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            // i use @PageableDefault to ensure the API returns a sensible default page if the user doesn't specify one
            @PageableDefault(size = 10) Pageable pageable) {
        return bookService.searchBooks(title, status, pageable);
    }
}