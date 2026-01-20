package com.railway.service;

import com.railway.dto.Bookingreq;
import com.railway.exception.Bookingexception;
import com.railway.logic.Customlogic;
import com.railway.logic.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Inventory implements Subscribe {

    private static final Logger log = LoggerFactory.getLogger(Inventory.class);

    // i am injecting the custom logic (broker) here so i can register this service as a listener.
    public Inventory(Customlogic logic) {
        logic.addListener(this);
    }

    @Override
    public void reqrecived(Bookingreq req) {
        log.info("processing {}", req.getBookingId());

        // i added this check specifically for the poison test scenario.
        if (req.getAge() < 0) {
            throw new Bookingexception("age is -ve write correct age: " + req.getAge());
        }

        try {
            // i put a sleep here to simulate that checking database/locking a seat takes some time.
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // if the system shuts down while sleeping, i restore the interrupt flag so the thread stops cleanly.
            Thread.currentThread().interrupt();
            log.error("Inventory thread interrupted", e);
        }

        log.info("seat booked");
    }
}