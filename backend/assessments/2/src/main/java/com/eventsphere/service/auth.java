package com.eventsphere.service;
import com.eventsphere.config.jwtservice;
import com.eventsphere.repo.userrepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class auth {

    // intern naming: snake_case and no private for some
final AuthenticationManager Auth_Manager;
private final jwtservice jwtService;
private final UserDetailsService userDetailsService;

private static final Logger log = LoggerFactory.getLogger(auth.class);

public String login(String user_name, String pass_word) {
log.info("somebody trying login: {}", user_name);
    Auth_Manager.authenticate(
                new UsernamePasswordAuthenticationToken(user_name, pass_word)
        );

log.info("login worked for {}, creating token...", user_name);

    UserDetails User_details = userDetailsService.loadUserByUsername(user_name);


        String final_token = jwtService.generateToken(User_details);
        return final_token;
    }
}