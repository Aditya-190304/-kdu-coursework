package com.smartlock.demo.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MonitorTime {
    // I added this so I can measure performance.
    // It's useful for seeing how long hardware checks (like battery) actually take.
}