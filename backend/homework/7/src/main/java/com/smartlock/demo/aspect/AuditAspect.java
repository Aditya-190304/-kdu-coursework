package com.smartlock.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    // I set this to run before the method starts, just to log that someone is trying to get in.
    @Before("@annotation(com.smartlock.demo.annotation.AuditLog)")
    public void logAccessAttempt(JoinPoint joinPoint) {
        log.info("ACCESS ATTEMPT: User is approaching the door");
    }

    // I only want this to run if the door opened successfully (no errors).
    @AfterReturning("@annotation(com.smartlock.demo.annotation.AuditLog)")
    public void logSuccess(JoinPoint joinPoint) {
        log.info("SUCCESS: User has entered the building");
    }
}