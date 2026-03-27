package com.ipze.config;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class GatewayServiceFilter implements GatewayFilter {

    private final JwtParseService jwtParseService;
    private final SecurityProperties securityProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if (securityProperties.getExcludedUrls()
                .stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getURI().getPath()))
        ) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7).trim();
            Claims claims = jwtParseService.extractClaims(token);

            String username = claims.getSubject();
            String userId = claims.get("userId", String.class);
            String role = claims.get("roles", String.class);

            ServerHttpRequest mutatedRequest = request.mutate()
                    .headers(headers -> {
                        headers.remove("X-User-Name");
                        headers.remove("X-User-ID");
                        headers.remove("X-User-Roles");
                        headers.set(HttpHeaders.AUTHORIZATION, authHeader);

                    })
                    .header("X-User-Name", username)
                    .header("X-User-ID", userId)
                    .header("X-User-Roles", role)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}