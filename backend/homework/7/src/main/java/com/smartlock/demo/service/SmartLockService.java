package com.smartlock.demo.service;

import com.smartlock.demo.annotation.AuditLog;
import com.smartlock.demo.annotation.MonitorTime;
import com.smartlock.demo.annotation.SecureAccess;
import com.smartlock.demo.annotation.SystemAlarm;
import com.smartlock.demo.exception.HardwareFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmartLockService {

    private static final Logger log = LoggerFactory.getLogger(SmartLockService.class);

    /*
     * I'm applying my custom annotations here.
     * @SecureAccess handles the authorization check.
     * @AuditLog handles the recording of the event.
     */
    @SecureAccess
    @AuditLog
    @SystemAlarm
    public void unlock(String user) {
        // I need to make sure the input isn't broken before doing anything else
        if (user == null || user.trim().isEmpty()) {
            throw new HardwareFailureException("Input string was empty.");
        }

        // If we get here, the door actually unlocks
        log.info("The door is now open for [{}]", user);
    }

    // I want to see how long the battery check takes, so I tagged it with @MonitorTime
    @MonitorTime
    public void checkBattery() {
        try {
            // I'm simulating a hardware delay here
            Thread.sleep(100);
            log.info("Battery level is stable.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}