package com.eventsphere.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long userId;

    @ManyToOne
    @JoinColumn(name = "event_id")
    event event;

int ticketsReserved;
String status;
LocalDateTime createdAt;


public void setUserId(Long userId) {
    this.userId = userId;
    }

public void setTicketsReserved(int ticketsReserved) {
    this.ticketsReserved = ticketsReserved;
    }

public void setStatus(String status) {
    this.status = status;
    }

public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    }


public Long getUserId() { return userId; }
public int getTicketsReserved() { return ticketsReserved; }
public String getStatus() { return status; }
}