package com.ipze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8082")
                .filter((request, next) -> {

                    var auth = SecurityContextHolder.getContext().getAuthentication();

                    ClientRequest.Builder req = ClientRequest.from(request);

                    if (auth != null) {

                        if (auth.getCredentials() instanceof String token) {
                            req.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                        }

                        if (auth.getPrincipal() instanceof LocalUserDetails user) {
                            req.header("X-User-ID", user.uuid());
                            req.header("X-User-Name", user.getUsername());
                            req.header("X-User-Roles", user.getRole());
                        }
                    }

                    return next.exchange(req.build());
                })
                .build();
    }
}
//@Bean
//public WebClient webClient(WebClient.Builder builder) {
//    return builder
//            .baseUrl("http://localhost:8082")
//            .filter((request, next) -> {
//
//                var auth = SecurityContextHolder.getContext().getAuthentication();
//
//                ClientRequest.Builder req = ClientRequest.from(request);
//
//                if (auth != null) {
//
//                    // SAFE JWT extraction (example)
//                    if (auth.getCredentials() instanceof String token) {
//                        req.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
//                    }
//
//                    if (auth.getPrincipal() instanceof LocalUserDetails user) {
//                        req.header("X-User-ID", user.uuid());
//                        req.header("X-User-Name", user.getUsername());
//                        req.header("X-User-Roles", user.getRole());
//                    }
//                }
//
//                return next.exchange(req.build());
//            })
//            .build();
//}