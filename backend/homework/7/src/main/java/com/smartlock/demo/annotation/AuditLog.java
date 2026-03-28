package com.smartlock.demo.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditLog {
    // I created this annotation to trigger access logging automatically.
    // Putting this on a method tells the system to record who called it.
}