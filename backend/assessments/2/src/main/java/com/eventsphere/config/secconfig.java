package com.eventsphere.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
public class secconfig {

    final jwtauthfilter jwt_filter;
    private final AuthenticationProvider Auth_Provider;

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // disable csrf because we are doing a REST API with tokens
http.csrf(c -> c.disable());

http.authorizeHttpRequests(req -> req.requestMatchers("/api/auth/**", "/h2-console/**").permitAll()
    .requestMatchers("/api/admin/**").hasRole("ADMIN")
    .requestMatchers("/api/user/**").hasRole("USER")
    .anyRequest().authenticated()
        );

// disable frame options so the H2 console works in the browser
http.headers(h -> h.frameOptions(f -> f.disable()));
http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
http.authenticationProvider(Auth_Provider);
// put the jwt filter before the user/password one
http.addFilterBefore(jwt_filter, UsernamePasswordAuthenticationFilter.class);

SecurityFilterChain final_chain = http.build();
return final_chain;
    }
}