package com.librarydomain.repo;

import com.librarydomain.Book;
import com.librarydomain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
    Optional<Loan> findByBookAndReturnedAtIsNull(Book book);
}