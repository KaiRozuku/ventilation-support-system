package com.ipze.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String authHeader =
                    request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader != null &&
                    authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7).trim();
                Claims claims = Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(
                                securityProperties
                                        .getSecretKey()
                                        .getBytes(StandardCharsets.UTF_8))
                        )
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                var authorities = Arrays.stream(
                                claims.get("roles", String.class).split(","))
                        .map(String::trim)
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                UserDetails user = new LocalUserDetails(
                        claims.get("userId", String.class),
                        claims.getSubject(),
                        authorities
                );

                var authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                authHeader,
                                authorities
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            log.warn("exc{}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}