package com.eventsphere.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @OneToOne
reserve theReservation;

    private Long user_id;



public void setReservation(reserve reservation) { this.theReservation = reservation; }
public void setUserId(Long userId) { this.user_id = userId; }

}