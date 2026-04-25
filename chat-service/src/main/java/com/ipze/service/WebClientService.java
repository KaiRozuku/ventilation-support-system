package com.ipze.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient webClient;

    public boolean userExists(String userId) {
        Boolean result = webClient
                .get()
                .uri("/auth-service/api/system/users/{id}", userId)
                .retrieve()
                .toBodilessEntity()
                .map(resp -> {
                    log.info("STATUS = {}", resp.getStatusCode());
                    return resp.getStatusCode().is2xxSuccessful();
                })
                .onErrorResume(e -> {
                    log.error("ERROR = {}", e.getMessage());
                    return Mono.just(false);
                })
                .defaultIfEmpty(false)
                .block();

        return !Boolean.TRUE.equals(result);
    }
}