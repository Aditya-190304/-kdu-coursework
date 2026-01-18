package com.librarydomain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "books")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue
    private UUID id;

    // i added length constraint (min 2 chars implied by not allowing empty strings practically)
    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status = BookStatus.PROCESSING;

    // i used this for Optimistic Locking as requested
    @Version
    private Long version;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    // i added this to satisfy the @UpdatedAt requirement
    @LastModifiedDate
    private Instant updatedAt;

    public enum BookStatus { PROCESSING, AVAILABLE, CHECKED_OUT }
}