package com.librarydomain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "loans") // i explicitly named the table "loans" to match postgres conventions
@Data // i use lombok here to avoid writing boilerplate getters/setters
@EntityListeners(AuditingEntityListener.class) // i added this listener so spring can auto-fill the creation date
public class Loan {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false) // i strictly require a book for every loan record
    private Book book;

    @ManyToOne(optional = false) // i strictly require a borrower to exist
    private User borrower;

    @CreatedDate // i let spring automatically set this timestamp on creation
    @Column(nullable = false, updatable = false) // i ensure the borrow date cannot be changed later
    private Instant borrowedAt;

    private Instant returnedAt; // i leave this null initially; if it's null, the loan is still active
}