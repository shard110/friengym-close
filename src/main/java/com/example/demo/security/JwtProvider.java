// JwtProvider.java
package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import java.util.Date;

public class JwtProvider {
    private static final String SECRET_KEY = "your-secret-key"; // Replace with your actual secret key
    private static SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000)) // 1 day expiration
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
