package com.smarthome.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device extends Base {

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

     private String customName;

    @OneToOne
     @JoinColumn(name = "inventory_id", unique = true)
   private Inventory inventoryDetails;

    @ManyToOne
     @JoinColumn(name = "room_id")
     @JsonIgnore
    private Room room;
}