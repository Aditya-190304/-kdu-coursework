package com.libraryweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity // i enabled this so i can use @PreAuthorize annotations if needed later
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable()) // i disable csrf because this is a stateless api, not a browser-based form app

                // i allow frames from the same origin so the H2 console (which uses frames) loads correctly
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                .authorizeHttpRequests(auth -> auth
                        // i strictly lock down write operations to specific roles
                        .requestMatchers(HttpMethod.POST, "/books").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.PATCH, "/books/*/catalog").hasRole("LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/loans/**").hasRole("MEMBER")

                        // i explicitly permit access to swagger and h2 console so i can debug without logging in
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()

                        .requestMatchers("/analytics/**").hasRole("LIBRARIAN")
                        .anyRequest().authenticated() // i require authentication for everything else by default
                )
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // i use bcrypt to ensure passwords are hashed securely before storage
        return new BCryptPasswordEncoder();
    }
}