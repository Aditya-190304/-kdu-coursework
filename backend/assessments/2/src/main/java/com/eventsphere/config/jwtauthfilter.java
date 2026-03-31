package com.eventsphere.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class jwtauthfilter extends OncePerRequestFilter {

    private final jwtservice Jwt_service;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

// printing the path so I know the filter is actually hitting the right endpoint
        System.out.println("filter is running on: " + req.getServletPath());

        String Auth_header = req.getHeader("Authorization");

if (Auth_header == null || Auth_header.startsWith("Bearer ") == false) {
            // if there's no bearer token just move to the next filter in the chain
chain.doFilter(req, res);
            return;
        }

String my_token = Auth_header.substring(7);
String User_email = Jwt_service.extractUsername(my_token);

if (User_email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

UserDetails User_details = userDetailsService.loadUserByUsername(User_email);

boolean ValidCheck = Jwt_service.isTokenValid(my_token, User_details);

            if (ValidCheck == true) {
                // creating the auth object manually since it's valid
                UsernamePasswordAuthenticationToken authObj = new UsernamePasswordAuthenticationToken(
                        User_details, null, User_details.getAuthorities()
                );

                authObj.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authObj);
            }
        }

        chain.doFilter(req, res);
    }
}