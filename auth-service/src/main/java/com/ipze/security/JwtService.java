package com.ipze.security;

import com.ipze.model.postgres.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtService {

    private final String secretKey;
    private final Long accessTokenTtl;
    private final Long refreshTokenTtl;

    public JwtService(@Value("${jwt.secret-key}") String secretKey,
                      @Value("${jwt.access-token-ttl}") Long accessTokenTtl,
                      @Value("${jwt.refresh-token-ttl}") Long refreshTokenTtl) {
        this.accessTokenTtl = accessTokenTtl;
        this.refreshTokenTtl = refreshTokenTtl;
        this.secretKey = secretKey;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username, UUID userId, Role role) {
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(username)
                .claim("roles", role.name())
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()
                        + accessTokenTtl))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(String username, UUID uuid) {
        return Jwts.builder()
                .subject(username)
                .claim("type", "refresh")
                .claim("userId", uuid.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenTtl))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UUID extractUUID(String token) {
        return Optional.ofNullable(extractClaims(token).get("userId", String.class))
                .map(UUID::fromString)
                .orElseThrow(() -> new IllegalArgumentException("Token missing userId"));
    }
    public String extractRole(String token) {
        return Optional.ofNullable(extractClaims(token).get("roles", String.class))
                .orElseThrow(() -> new IllegalArgumentException("Token missing role"));
    }
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
}