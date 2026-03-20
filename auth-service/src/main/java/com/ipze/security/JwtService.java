package com.ipze.security;

import com.ipze.model.postgres.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username, Role role) {
        return Jwts.builder()
                .subject(username)
                .claim("authorities", role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()
                        + TimeUnit.MINUTES.toMillis(30)))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()
                        + TimeUnit.HOURS.toMillis(24)))
                .signWith(getSigningKey())
                .compact();
    }
}