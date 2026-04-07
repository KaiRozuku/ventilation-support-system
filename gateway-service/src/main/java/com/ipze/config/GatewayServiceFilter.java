package com.ipze.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class GatewayServiceFilter implements GatewayFilter {

    private final SecurityProperties securityProperties;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (securityProperties.excludedUrls().stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, path))) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return sendResponse(exchange, HttpStatus.UNAUTHORIZED);
        }

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(securityProperties.secretKey().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(authHeader.substring(7).trim())
                    .getPayload();

            String jti = claims.getId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:jti:" + jti))) {
                return sendResponse(exchange, HttpStatus.UNAUTHORIZED);
            }

            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-User-Name", claims.getSubject())
                    .header("X-User-ID", claims.get("userId", String.class))
                    .header("X-User-Roles", claims.get("roles", String.class))
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (ExpiredJwtException e) {
            log.warn("JWT expired: {}", e.getMessage());
            exchange.getResponse().getHeaders().add("X-Token-Expired", "true");
            return sendResponse(exchange, HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            log.error("Invalid JWT: {}", e.getMessage());
            return sendResponse(exchange, HttpStatus.FORBIDDEN);
        }
    }

    private Mono<Void> sendResponse(ServerWebExchange exchange, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }
}