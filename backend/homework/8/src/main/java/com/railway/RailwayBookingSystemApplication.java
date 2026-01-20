package com.railway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RailwayBookingSystemApplication {

    private static final Logger log = LoggerFactory.getLogger(RailwayBookingSystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RailwayBookingSystemApplication.class, args);
        log.info("railway application started");
    }
}