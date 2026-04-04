package com.hospital.staffing.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "staff_user")
@EntityListeners(AuditingEntityListener.class) 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING) 
    private UserRole role;

    @CreatedDate
    @Column(nullable = false, updatable = false) 
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false) 
    private LocalDateTime updatedAt;

}
