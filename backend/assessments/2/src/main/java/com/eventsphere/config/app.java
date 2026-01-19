package com.eventsphere.config;

import com.eventsphere.repo.userrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class app {
    final userrepo userRepo;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // fetching user from db manually and then mapping it to spring security user
            Optional<com.eventsphere.entity.user> foundUser = userRepo.findByUsername(username);
            if (foundUser.isEmpty()) {
                throw new UsernameNotFoundException("oops user not found!");
            }

            com.eventsphere.entity.user My_user = foundUser.get();
        return User.builder()
            .username(My_user.getUsername())
            .password(My_user.getPassword())
            .roles(My_user.getRole())
            .build();
        };
    }

    @Bean
 public AuthenticationProvider authenticationProvider() {
        // using dao provider because that's what the tutorial recommended
    DaoAuthenticationProvider auth_provider = new DaoAuthenticationProvider();
    auth_provider.setUserDetailsService(userDetailsService());
    auth_provider.setPasswordEncoder(passwordEncoder());
        return auth_provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // bcyrpt for safe password storage
        return new BCryptPasswordEncoder();
    }
}