package com.eventsphere.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

String Name_of_Event;
int ticketCount;


public int getTicketCount() { return this.ticketCount; }
public void setTicketCount(int ticketCount) { this.ticketCount = ticketCount; }
}