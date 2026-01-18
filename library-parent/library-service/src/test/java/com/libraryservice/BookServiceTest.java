package com.libraryservice;

import com.libraryapi.BookResponse;
import com.librarydomain.Book;
import com.librarydomain.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepo;

    @InjectMocks
    private BookService bookService;

    @Test
    void catalog_ShouldSucceed_WhenStatusIsProcessing() {
        // Arrange
        UUID id = UUID.randomUUID();
        Book book = new Book();
        book.setId(id);
        book.setStatus(Book.BookStatus.PROCESSING);
        book.setTitle("Test Book");

        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        // Act
        BookResponse response = bookService.catalog(id);

        // Assert
        assertEquals("AVAILABLE", response.getStatus());
    }

    @Test
    void catalog_ShouldThrow_WhenStatusIsNotProcessing() {
        // Arrange
        UUID id = UUID.randomUUID();
        Book book = new Book();
        book.setId(id);
        book.setStatus(Book.BookStatus.CHECKED_OUT); // Wrong status

        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> bookService.catalog(id));
    }
}