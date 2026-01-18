package com.libraryservice;

import com.libraryapi.LoanResponse;
import com.librarydomain.Book;
import com.librarydomain.Loan;
import com.librarydomain.User;
import com.librarydomain.repo.BookRepository;
import com.librarydomain.repo.LoanRepository;
import com.librarydomain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {

    private final BookRepository bookRepo;
    private final LoanRepository loanRepo;
    private final UserRepository userRepo;

    @Transactional // i use transactional to ensure the book status and loan record update atomically
    public LoanResponse borrowBook(UUID bookId, String username) {
        log.info("User {} borrowing book {}", username, bookId);

        // i verify the user and book exist before proceeding
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getStatus() != Book.BookStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available");
        }

        // i rely on the @Version field in Book to fail here if two people try at once
        book.setStatus(Book.BookStatus.CHECKED_OUT);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setBorrower(user);

        return mapToResponse(loanRepo.save(loan));
    }

    @Transactional
    public void returnBook(UUID bookId, String username) {
        log.info("User {} returning book {}", username, bookId);

        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getStatus() != Book.BookStatus.CHECKED_OUT) {
            throw new IllegalStateException("Book is not checked out");
        }

        // i find the specific active loan record for this book
        Loan loan = loanRepo.findByBookAndReturnedAtIsNull(book)
                .orElseThrow(() -> new IllegalStateException("No active loan"));

        // i enforce a security check so only the actual borrower can return the book
        if (!loan.getBorrower().getUsername().equals(username)) {
            log.warn("Security: User {} tried returning book of {}", username, loan.getBorrower().getUsername());
            throw new IllegalStateException("You are not the borrower");
        }

        // i mark the loan as returned and make the book available again immediately
        loan.setReturnedAt(Instant.now());
        book.setStatus(Book.BookStatus.AVAILABLE);
        loanRepo.save(loan);
    }

    private LoanResponse mapToResponse(Loan loan) {
        LoanResponse resp = new LoanResponse();
        resp.setId(loan.getId());
        resp.setBookTitle(loan.getBook().getTitle());
        resp.setBorrowerUsername(loan.getBorrower().getUsername());
        resp.setBorrowedAt(loan.getBorrowedAt());
        resp.setReturnedAt(loan.getReturnedAt());
        return resp;
    }
}