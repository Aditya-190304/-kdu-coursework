package com.libraryservice.security;

import com.librarydomain.User;
import com.librarydomain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService { // i implement this interface to tell spring security how to load users from my db

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // i try to find the user in my database, or throw a specific exception if they don't exist
        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // i map my custom domain entity into the standard spring security user object
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword()) // i pass the hashed password here so spring can verify it
                .roles(u.getRole().name()) // i map my enum role directly to a spring security authority
                .build();
    }
}