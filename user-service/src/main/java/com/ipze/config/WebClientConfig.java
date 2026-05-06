package com.ipze.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8082")
                .filter((request, next) -> {

                    var auth = SecurityContextHolder.getContext().getAuthentication();

                    ClientRequest.Builder req = ClientRequest.from(request);

                    if (auth != null && auth.getCredentials() instanceof String token) {
                        req.header(HttpHeaders.AUTHORIZATION, token.startsWith("Bearer ") ? token : "Bearer " + token);
                    }

                    return next.exchange(req.build());
                })
                .build();
    }

    @Bean
    public WebClient dataWebClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8085")
                .filter(authorizationFilter())
                .build();
    }

    private ExchangeFilterFunction authorizationFilter() {
        return (request, next) -> {

            String auth = request.headers()
                    .getFirst(HttpHeaders.AUTHORIZATION);
            log.info("auth token-> {}", auth);
            if (auth != null) {
                ClientRequest newRequest = ClientRequest.from(request)
                        .header(HttpHeaders.AUTHORIZATION, auth)
                        .build();

                return next.exchange(newRequest);
            }

            return next.exchange(request);
        };
    }
}