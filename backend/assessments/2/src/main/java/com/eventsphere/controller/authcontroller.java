package com.eventsphere.controller;
import com.eventsphere.dto.login;
import com.eventsphere.service.auth;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class authcontroller {

    private final auth authservice;
    private static final Logger log = LoggerFactory.getLogger(authcontroller.class);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody login req) {
        // logging the username just to make sure the request body is parsing right
        log.info("trying to login user: " + req.getUsername());

        String Token_val = authservice.login(req.getUsername(), req.getPassword());

        if (Token_val == null) {
            log.error("token is empty!!");
            // if it's null then something went wrong with the auth service
            throw new RuntimeException("login failed lol");
        }

        String final_token = Token_val;

        return ResponseEntity.ok(final_token);
    }
}