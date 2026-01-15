package com.smartlock.demo.aspect;

import com.smartlock.demo.exception.UnauthorizedAccessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    private static final Logger log = LoggerFactory.getLogger(SecurityAspect.class);

    // This is my stopwatch logic. I wrap the method to calculate the execution time.
    @Around("@annotation(com.smartlock.demo.annotation.MonitorTime)")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // I run the actual method here
        Object result = joinPoint.proceed();

        long timeTaken = System.currentTimeMillis() - start;
        log.info("Operation took {} ms", timeTaken);

        return result;
    }

    /*
     * I use this as a gatekeeper.
     * If the user is 'Unknown', I block them completely and don't even let the service run.
     */
    @Around("@annotation(com.smartlock.demo.annotation.SecureAccess) && args(user,..)")
    public Object checkPermissions(ProceedingJoinPoint joinPoint, String user) throws Throwable {

        if ("Unknown".equalsIgnoreCase(user)) {
            log.warn("SECURITY ALERT: Unauthorized access blocked!");

            throw new UnauthorizedAccessException("Access Denied: User is unknown.");
        }

        return joinPoint.proceed();
    }
}