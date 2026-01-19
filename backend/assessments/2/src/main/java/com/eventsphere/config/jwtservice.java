package com.eventsphere.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class jwtservice {

    @Value("${jwt.secret}")
    String secretKey;

    public String extractUsername(String token) {
        Claims all_claims = extractAllClaims(token);
        String Token_Name = all_claims.getSubject();
        return Token_Name;
    }

    public String generateToken(UserDetails user) {
        Map<String, Object> my_claims = new HashMap<>();

// setting it for 10 hours so I don't have to keep logging in while testing
        long CurrentTime = System.currentTimeMillis();
        long ExpireTime = 1000 * 60 * 60 * 10;

        return Jwts.builder()
                .setClaims(my_claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(CurrentTime))
                .setExpiration(new Date(CurrentTime + ExpireTime))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        String name_from_token = extractUsername(token);
        boolean check = name_from_token.equals(user.getUsername());

        Claims c = extractAllClaims(token);
        boolean not_expired = c.getExpiration().after(new Date());

        return (check && not_expired);
    }

    private Claims extractAllClaims(String token) {
        // using the parser builder to get all the data out of the jwt
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        // decode the secret from the properties file
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}