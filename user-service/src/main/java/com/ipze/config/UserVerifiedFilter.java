package com.ipze.config;

import com.ipze.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserVerifiedFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/auth/login") || path.startsWith("/auth/register") || path.startsWith("/auth/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = request.getHeader("X-User-Name");
        String rolesHeader = request.getHeader("X-User-Roles");

        logger.info("X-User-Roles: " + rolesHeader);
        if (!StringUtils.hasText(username) || !StringUtils.hasText(rolesHeader)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new UnauthorizedException("Missing username or role");
        }

        var authorities = Arrays.stream(rolesHeader.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .map(SimpleGrantedAuthority::new)
                .toList();

        var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
//}package com.ipze.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.List;
//
//@Component
//public class UserVerifiedFilter extends OncePerRequestFilter {
//
//    @Value("${jwt.secret-key}")
//    private String secretKey; // твій ключ JWT
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if(authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            try {
//                Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//
//                Claims claims = Jwts.parserBuilder()
//                        .setSigningKey(key)
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//
//                String username = claims.getSubject();
//                String role = claims.get("authorities", String.class);
//
//                var authorities = List.of(new SimpleGrantedAuthority(role));
//                var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
//                SecurityContextHolder.getContext().setAuthentication(auth);
//
//            } catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}