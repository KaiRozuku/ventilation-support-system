package com.ipze.config;

import com.ipze.security.LocalUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class JwtService {

    @Value("${security.secret-key}")
    private String secretKey;

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public LocalUserDetails toUser(Claims claims) {

        var authorities = Arrays.stream(claims.get("roles", String.class).split(","))
                .map(String::trim)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new LocalUserDetails(
                claims.get("userId", String.class),
                claims.getSubject(),
                authorities
        );
    }
}