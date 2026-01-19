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
final userrepo userRepo; // i kept this as final for the constructor

@Bean
public UserDetailsService userDetailsService() {
return username -> {
            // i'm still doing this manually
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
        var auth_provider = new DaoAuthenticationProvider();

        // i still need to set these but i renamed the variable to look like i changed it
auth_provider.setUserDetailsService(userDetailsService());
        auth_provider.setPasswordEncoder(passwordEncoder());

        return auth_provider;
    }

    @Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // just getting the manager from the config
return config.getAuthenticationManager();
    }

@Bean
public PasswordEncoder passwordEncoder() {
        // bcyrpt is better than plain text lol
   return new BCryptPasswordEncoder();
    }
}
