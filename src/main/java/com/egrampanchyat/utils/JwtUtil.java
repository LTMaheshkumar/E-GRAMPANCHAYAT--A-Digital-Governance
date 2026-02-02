package com.egrampanchyat.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // ⚠️ IMPORTANT: keep key final and single
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    // ✅ TOKEN GENERATION
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // ✅ COMMON METHOD TO GET CLAIMS
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ EXTRACT EMAIL (SUBJECT)
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ EXTRACT ROLE (THIS WAS MISSING)
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // ✅ VALIDATE TOKEN
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
