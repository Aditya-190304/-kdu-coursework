package com.railway.service;

import com.railway.dto.Bookingreq;
import com.railway.entity.Paymenthistory;
import com.railway.logic.Customlogic;
import com.railway.logic.Subscribe;
import com.railway.repo.Paymentrepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Payment implements Subscribe {

    private static final Logger log = LoggerFactory.getLogger(Payment.class);
    private final Paymentrepo repo;

    public Payment(Customlogic logic, Paymentrepo repo) {
        this.repo = repo;
        // i am registering myself as a listener immediately upon startup.
        logic.addListener(this);
    }

    @Override
    public void reqrecived(Bookingreq req) {
        // i check if this booking ID is already in the database.
        // if the broker retries a message (due to a crash or delay), i don't want to charge the user twice.
        if (repo.existsById(req.getBookingId())) {
            log.warn("payment has been duplicated {}", req.getBookingId());
            return;
        }

        // i am saving the payment record to the database. this acts as the "proof of payment".
        repo.save(new Paymenthistory(req.getBookingId()));
        log.info("money dedeucted");
    }
}