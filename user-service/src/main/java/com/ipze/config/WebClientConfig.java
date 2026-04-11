package com.ipze.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static final Logger log = LoggerFactory.getLogger(WebClientConfig.class);

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8082")
                .filter((request, next) -> {

                    var auth = SecurityContextHolder.getContext().getAuthentication();

                    ClientRequest.Builder req = ClientRequest.from(request);

                    if (auth != null && auth.getPrincipal() instanceof LocalUserDetails user) {
                        log.info("TOken: {}", auth.getCredentials());
                        req.header(HttpHeaders.AUTHORIZATION, (String) auth.getCredentials());
                        req.header("X-User-ID", user.uuid());
                        req.header("X-User-Name", user.getUsername());
                        req.header("X-User-Roles", user.getRole());
                    }

                    return next.exchange(req.build());
                })
                .build();
    }
}