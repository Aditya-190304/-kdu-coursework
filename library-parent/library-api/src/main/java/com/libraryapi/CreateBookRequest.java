package com.libraryapi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO used for creating a new book in the system.
 * Contains validation rules to ensure title integrity.
 */
@Data
public class CreateBookRequest {

    /**
     * The title of the book. Must be between 2 and 200 characters.
     */
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 chars")
    private String title;
}