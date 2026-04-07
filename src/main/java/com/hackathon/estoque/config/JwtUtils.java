package com.hackathon.estoque.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private Algorithm getSigningAlgorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(Date.from(Instant.now().plus(jwtExpirationMs, ChronoUnit.MILLIS)))
                .sign(getSigningAlgorithm());
    }

    public String getUserNameFromJwtToken(String token) {
        DecodedJWT jwt = JWT.require(getSigningAlgorithm())
                .build()
                .verify(token);
        return jwt.getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            JWT.require(getSigningAlgorithm())
                    .build()
                    .verify(authToken);
            return true;
        } catch (JWTVerificationException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }
}
