package com.smartlock.demo.aspect;

import com.smartlock.demo.annotation.SystemAlarm;
import com.smartlock.demo.exception.UnauthorizedAccessException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AlertAspect {

    private static final Logger log = LoggerFactory.getLogger(AlertAspect.class);

    /*
     * I updated this to look for the @SystemAlarm annotation.
     * Now, any method I tag with that sticker is automatically protected.
     */
    @AfterThrowing(
            pointcut = "@annotation(com.smartlock.demo.annotation.SystemAlarm)",
            throwing = "ex"
    )
    public void triggerAlarm(Exception ex) {

        // I ignore my custom security exception so the alarm doesn't ring
        // just because a user was blocked.
        if (ex instanceof UnauthorizedAccessException) {
            return;
        }

        log.error("ALARM TRIGGERED: System error detected: {}", ex.getMessage());
    }
}