package com.smarthome.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
 import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Data
 @MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
// parent class that adds audit timestamps to every entity automatically
public class Base {

    // records exactly when this entry was first created
     @CreatedDate
    @Column(updatable = false)
     private LocalDateTime createdDate;

    // updates automatically whenever we save changes to the data
    @LastModifiedDate
    private LocalDateTime modifiedDate;

      private LocalDateTime deletedDate;
}