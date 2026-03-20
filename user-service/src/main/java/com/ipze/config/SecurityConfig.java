package com.ipze.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//package com.ipze.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Configuration
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
////    private final JwtVerifierFilter jwtVerifierFilter;
//    @Bean
//    public SecurityFilterChain securityFilterChain (HttpSecurity http)  throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/hello", "/api/", "/api/home", "/api/main")
//                        .permitAll()
//                        .anyRequest().permitAll()
//                )
//                .addFilterBefore(gatewayAuthFilter(), UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//    @Bean
//    public OncePerRequestFilter gatewayAuthFilter() {
//        return new OncePerRequestFilter() {
//            @Override
//            protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                            @NonNull HttpServletResponse response,
//                                            @NonNull FilterChain filterChain) throws IOException, jakarta.servlet.ServletException {
//
//                String username = request.getHeader("X-User-Name");
//                String rolesHeader = request.getHeader("X-User-Roles");
//                System.out.println(username + "\n" + rolesHeader);
//                if(username != null && rolesHeader != null){
//                    List<SimpleGrantedAuthority> authorities = Arrays.stream(rolesHeader.split(","))
//                            .map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toList());
//
//                    var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//
//                filterChain.doFilter(request, response);
//            }
//        };
//    }
//}


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserVerifiedFilter userVerifiedFilter;

    @Bean
    public SecurityFilterChain filterChainUser(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register", "/auth/refresh").permitAll()
                        .requestMatchers("/user/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(userVerifiedFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}