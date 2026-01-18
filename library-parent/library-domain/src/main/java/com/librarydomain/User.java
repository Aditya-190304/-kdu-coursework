package com.librarydomain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
// i added this listener so Spring automatically populates @CreatedDate
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password; // BCrypt hash stored here

    @Enumerated(EnumType.STRING)
    private Role role;

    // i added this field with a default of true so users can login immediately
    private boolean enabled = true;

    // i added this to track when the user registered
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public enum Role { LIBRARIAN, MEMBER }
}