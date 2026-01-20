package com.railway.logic;

import com.railway.dto.Bookingreq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class Customlogic {

    private static final Logger log = LoggerFactory.getLogger(Customlogic.class);

    // i used a blocking queue here so the controller can just dump the request and leave.
    private final BlockingQueue<Bookingreq> mainQueue = new LinkedBlockingQueue<>();
    // this is the "Dead Letter Queue". i made this so if a message fails 3 times,
    private final BlockingQueue<Bookingreq> dlq = new LinkedBlockingQueue<>();
    // i use this list to keep track of everyone who needs to know about a new booking
    private final List<Subscribe> listeners = new ArrayList<>();
    // this is for services to register themselves so i know who to send messages to.
    public void addListener(Subscribe sub) {
        listeners.add(sub);
    }

    // the controller calls this. i did this so we can acknowledge the user immediately
    // while processing happens in the background.
    public void sendToQueue(Bookingreq req) {
        log.info("request recived {}", req.getBookingId());
        mainQueue.offer(req);
    }

    // i used @PostConstruct so this starts automatically when the app boots up.
    @PostConstruct
    public void initWorker() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    // i used .take() here so the thread waits efficiently if the queue is empty,
                    Bookingreq req = mainQueue.take();
                    distribute(req);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        t.setName("worker_queue");
        t.start();
    }

    private void distribute(Bookingreq req) {
        for (Subscribe sub : listeners) {
            // i am creating a new thread for each subscriber here.
            // i did this so if one service (like inventory) is slow or crashes,
            // it doesn't block the others (like notification).
            new Thread(() -> processSafely(sub, req)).start();
        }
    }

    // i wrote this method to handle the fault tolerance requirement.
    // instead of failing immediately, it tries to process the message securely.
    private void processSafely(Subscribe sub, Bookingreq req) {
        int tries = 0;
        boolean success = false;

        // i used a while loop here to retry exactly 3 times
        while (tries < 3 && !success) {
            try {
                tries++;
                sub.reqrecived(req);
                success = true;
            } catch (Exception e) {
                log.error(" broker for queue : {} failed for {}", tries, sub.getClass().getSimpleName());

                // if we hit the limit (3 tries), i move it to the DLQ so we can inspect it later.
                if (tries >= 3) {
                    log.error("too many meassage failed moving {} to dlq", req.getBookingId());
                    dlq.offer(req);
                } else {
                    // i added a sleep here so we don't spam the service immediately.
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        log.error("Retry sleep interrupted", ex);
                    }
                }
            }
        }
    }
}