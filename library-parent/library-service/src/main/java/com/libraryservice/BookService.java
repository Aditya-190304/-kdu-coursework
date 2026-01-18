package com.libraryservice;

import com.libraryapi.BookResponse;
import com.libraryapi.CreateBookRequest;
import com.librarydomain.Book;
import com.librarydomain.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j // i use slf4j here for standardized logging across the service layer
public class BookService {

    private final BookRepository bookRepo;

    public BookResponse create(CreateBookRequest req) {
        log.info("Creating book: {}", req.getTitle());
        Book book = new Book();
        book.setTitle(req.getTitle());
        // i save the entity first and then map it to a response DTO to keep the API layer clean
        return mapToResponse(bookRepo.save(book));
    }

    @Transactional // i mark this transactional to ensure data integrity during the status update
    public BookResponse catalog(UUID id) {
        log.info("Cataloging book {}", id);
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        // i strictly enforce the state transition rule: only PROCESSING books can become AVAILABLE
        if (book.getStatus() != Book.BookStatus.PROCESSING) {
            throw new IllegalStateException("Book must be PROCESSING to catalog");
        }

        book.setStatus(Book.BookStatus.AVAILABLE);
        return mapToResponse(book);
    }

    public Page<BookResponse> searchBooks(String title, String statusStr, Pageable pageable) {
        Book.BookStatus status = null;
        if (statusStr != null && !statusStr.isBlank()) {
            try {
                // i try to parse the status string, but i quietly ignore invalid values to make the API robust
                status = Book.BookStatus.valueOf(statusStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.debug("Invalid status ignored: {}", statusStr);
            }
        }
        // i use the map function here to transform the Page of entities into a Page of DTOs
        return bookRepo.findByFilters(status, title, pageable).map(this::mapToResponse);
    }

    // i added this method to satisfy the groupingBy + counting requirement
    public Map<String, Long> getBookStatusCounts() {
        log.info("Calculating book status analytics...");

        // i stream all books and use collectors to group them by status name and count them
        return bookRepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        book -> book.getStatus().name(),
                        Collectors.counting()
                ));
    }

    private BookResponse mapToResponse(Book b) {
        BookResponse resp = new BookResponse();
        resp.setId(b.getId());
        resp.setTitle(b.getTitle());
        resp.setStatus(b.getStatus().name());
        return resp;
    }
}