package com.railway.controller;

import com.railway.dto.Bookingreq;
import com.railway.logic.Customlogic;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class Booking {

    private final Customlogic logic;

    public Booking(Customlogic logic) {
        this.logic = logic;
    }

    @PostMapping
    public String create(@RequestBody Bookingreq req) {
        // if the user didn't provide an ID (like in postman), i generate one here so we can track it
        if (req.getBookingId() == null) {
            req.setBookingId(UUID.randomUUID().toString());
        }

        // pushing to the queue instead of processing it here.
        // this way the user doesn't have to wait for the inventory/payment to finish.
        logic.sendToQueue(req);

        // sending immediate response (ack) so the user knows we got the request
        return "booking " + req.getBookingId() + " received.";
    }
}