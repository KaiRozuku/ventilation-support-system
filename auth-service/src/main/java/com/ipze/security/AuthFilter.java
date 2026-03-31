package com.ipze.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String username = request.getHeader("X-User-Name");
        String userId = request.getHeader("X-User-ID");
        String rolesHeader = request.getHeader("X-User-Roles");

        if (username != null && userId != null && rolesHeader != null) {

            var authorities = Arrays.stream(rolesHeader.split(","))
                    .map(String::trim)
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            var authentication = new UsernamePasswordAuthenticationToken(
                    new ApplicationUserDetails(userId, username, authorities),
                    null,
                    authorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}