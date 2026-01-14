package com.hospital.staffing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // Import added

@SpringBootApplication
@EnableJpaAuditing // <--- FIX: Enables automatic timestamps
public class HospitalStaffingApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalStaffingApplication.class, args);
    }
}