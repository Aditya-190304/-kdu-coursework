package com.librarydomain.repo;

import com.librarydomain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    // i wrote this query to handle the optional filters gracefully (null checks inside query)
    @Query("SELECT b FROM Book b WHERE " +
            "(:status IS NULL OR b.status = :status) AND " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    Page<Book> findByFilters(
            @Param("status") Book.BookStatus status,
            @Param("title") String title,
            Pageable pageable
    );
}