package com.example.user_api.service;

import com.example.user_api.exception.TokenGeneratorException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(UUID userId) throws TokenGeneratorException {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", userId);
            Date now = new Date(System.currentTimeMillis());
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userId.toString())
                    .setIssuedAt(now)
                    .setExpiration(Date.from(
                            now.toInstant()
                                    .plusMillis(expiration)))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new TokenGeneratorException(e.getMessage());
        }

    }


    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

}
