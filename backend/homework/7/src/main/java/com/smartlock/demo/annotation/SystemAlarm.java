package com.smartlock.demo.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SystemAlarm {
    // I use this marker to tell the AlertAspect:
    // "If this method crashes, sound the alarm!"
}