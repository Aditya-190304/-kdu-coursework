package com.smarthome.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory extends Base {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(unique = true, nullable = false)
    // unique hardware id for device validation
    private String kickstonId;

    // credentials for device authentication
     private String deviceUsername;
    private String devicePassword;

    // when the device was made
    private LocalDateTime manufactureDateTime;

    // factory location where the device was built
     private String manufactureFactoryPlace;
}