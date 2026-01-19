package com.examensbe.backend.Jwt;


import com.examensbe.backend.services.UserEntityDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.nio.charset.StandardCharsets;

import java.util.Date;

@Component
public class JwtTokenGenerator {

    private final UserEntityDetailsService userEntityDetailsService;
    private final Key signingKey;
    private final long jwtExpirationInMs;

    @Autowired
    public JwtTokenGenerator(
            UserEntityDetailsService userEntityDetailsService,
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long jwtExpirationInMs
    ) {
        this.userEntityDetailsService = userEntityDetailsService;
        // Säkerställ att secret är minst 256-bitars (32 bytes)
        if (secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 256 bits");
        }
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationInMs = jwtExpirationInMs;
    }
     // Genererar JWT-token för användaren
    public String generateToken(Authentication authentication, String username) {
        UserDetails userDetails = userEntityDetailsService.loadUserByUsername(authentication.getName());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(signingKey)
                .compact();
    }
    // Hämtar användarnamn från JWT-token
    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    // Validerar JWT-token
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(authToken);
            return true;

        } catch (JwtException | IllegalArgumentException ex) {
            System.out.println("Invalid JWT: " + ex.getMessage());
            return false;
        }
    }


}