package com.eventsphere.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    // i used String for this because UUID returns a string anyway
String Trans_id;
private LocalDateTime date_time;

    // making these manual because lombok was giving me errors in the service
public void setTransactionId(String tid) {
        this.Trans_id = tid;} // mapping tid to Trans_id

public void setTransactionDate(LocalDateTime dt) {
    this.date_time = dt; // i did this so i can set the current time
}
}