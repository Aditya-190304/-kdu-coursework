package com.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_history")
public class Paymenthistory {

    // i am setting bookingId as the primary key (@Id) for mainly idempotence.
    // since IDs must be unique, looking this up helps us prevent double charging.
    @Id
    private String bookingId;

    private LocalDateTime paidAt;

    public Paymenthistory() {}

    public Paymenthistory(String bookingId) {
        this.bookingId = bookingId;
        //  recording the current time when the payment object is created
        this.paidAt = LocalDateTime.now();
    }
}
