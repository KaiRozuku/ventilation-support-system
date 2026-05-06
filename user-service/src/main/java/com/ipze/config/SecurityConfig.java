package com.ipze.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserFilter authUserFilter;

    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/user/account/**").authenticated()
                        .requestMatchers("/user/chat/**").authenticated()
                        .requestMatchers("/user/data/**").permitAll()
                        .requestMatchers("/user/analytic/predict/**").permitAll()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/analytic/metrics/**").authenticated()
                        .requestMatchers("/analytic/predict/**").authenticated()
                        .requestMatchers("/data/**").permitAll()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(authUserFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}