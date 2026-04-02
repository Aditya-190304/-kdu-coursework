package com.smarthome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
    public class Jpaconfig {
    // This separate class keeps JPA config away from Controller tests
}