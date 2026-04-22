package com.ipze.security;

import com.ipze.model.postgres.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
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

    public String generateAccessToken(String email, UUID userId, Role role) {
        return generateToken(
                email,
                Map.of(
                "roles", role.name(),
                "userId", userId.toString()
                ),
                accessTokenTtl);
    }

    public String generateRefreshToken(String email, UUID uuid) {
        return generateToken(
                email,
                Map.of(
                        "type", "refresh",
                        "userId", uuid.toString()
                ),
                refreshTokenTtl
        );
    }

    public String generateSystemToken(String serviceName) {
        return generateToken(
                serviceName,
                Map.of(
                        "type", Role.SYSTEM.name(),
                        "roles", Role.SYSTEM.name()
                ),
                accessTokenTtl
        );
    }

    private Claims extractFromToken(String token){
    return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String extractJti(String token){
        return extractFromToken(token).getId();
    }

    public long extractExpiration(String token){
        return extractFromToken(token).getExpiration().getTime();
    }

    private String generateToken(String subject, Map<String, String> claims, long ttlMillis){
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(now + ttlMillis))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
}