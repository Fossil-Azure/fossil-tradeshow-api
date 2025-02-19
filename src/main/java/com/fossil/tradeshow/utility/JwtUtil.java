package com.fossil.tradeshow.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Base64-encoded 256-bit key
    private static final String SECRET_KEY = "JJj7UEDM2B3sXlbE0EMCzFrxUVjXSH+L0pawaneatlM=";
    private final SecretKey key;

    public JwtUtil() {
        // Decode the Base64 key to a SecretKey
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    // Generate JWT Token using modern builder-style methods
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username) // Use modern method for subject
                .issuedAt(new Date()) // Use modern method for issuedAt
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key) // Sign with the SecretKey
                .compact(); // Build and return the JWT token
    }

    // Extract Username from the Token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Validate the Token
    public boolean validateToken(String token) {
        try {
            extractClaims(token); // Parse and validate claims
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extract Claims
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key) // Use verifyWith instead of setSigningKey
                .build() // Build the parser
                .parseSignedClaims(token) // Parse the token
                .getPayload();
    }
}