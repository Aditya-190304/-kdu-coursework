package com.smartlock.demo.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecureAccess {
    // I use this tag for methods that need protection.
    // The aspect will check if the user is allowed before running any method with this annotation.
}