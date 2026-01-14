package com.hospital.staffing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // <--- ADD THIS IMPORT
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ShiftType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeName;

    @OneToMany(mappedBy = "shiftType", cascade = CascadeType.ALL)
    @JsonIgnore // <--- ADD THIS ANNOTATION to stop the infinite loop
    private List<Shift> shifts;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}