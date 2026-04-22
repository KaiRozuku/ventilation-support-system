package com.ipze.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient webClient;

    public boolean validateUserExists(String userId) {
        Boolean result = webClient
                .get()
                .uri("/auth-service/api/creator/users/{id}", userId)
                .retrieve()
                .toBodilessEntity()
                .map(resp -> {
                    System.out.println("STATUS = " + resp.getStatusCode());
                    return resp.getStatusCode().is2xxSuccessful();
                })
                .onErrorResume(e -> {
                    System.out.println("ERROR = " + e.getMessage());
                    return Mono.just(false);
                })
                .defaultIfEmpty(false)
                .block();

        return Boolean.TRUE.equals(result);
    }
}