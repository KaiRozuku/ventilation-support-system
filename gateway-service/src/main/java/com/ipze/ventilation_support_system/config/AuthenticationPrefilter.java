package com.ipze.ventilation_support_system.config;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationPrefilter implements GatewayFilter {

    private final JwtParseService jwtParseService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        log.info("Request URL: {}", path);

        if (path.startsWith("/auth/")) return chain.filter(exchange);

        String authHeader = request
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        log.info("Authorization header: {}", authHeader);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {


            String token = authHeader.substring(7).trim();
            Claims claims = jwtParseService.extractClaims(token);

            String username = claims.getSubject();
            String role = claims.get("authorities", String.class);

            log.info("Username: {}", username);
            log.info("role: {}", role);

            ServerHttpRequest mutatedRequest = request
                    .mutate()
                    .header("X-User-Name", username)
                    .header("X-User-Roles", role)
                    .build();

            return chain.filter(exchange
                    .mutate()
                    .request(mutatedRequest)
                    .build()
            );

        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}