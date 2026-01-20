package com.railway.service;

import com.railway.dto.Bookingreq;
import com.railway.logic.Customlogic;
import com.railway.logic.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Notification implements Subscribe {

    private static final Logger log = LoggerFactory.getLogger(Notification.class);

    // i inject the broker (customlogic) here so i can register this service as a listener.
    // i did this so that when the app starts, the broker immediately knows to send messages here too.
    public Notification(Customlogic logic) {
        logic.addListener(this);
    }

    @Override
    public void reqrecived(Bookingreq req) {
        log.info("notified: {}", req.getUserId());
    }
}