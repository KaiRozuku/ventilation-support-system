package com.ipze.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class WebClientUtils{

    private final WebClient webClient;

    <T, R> Mono<R> sendPutRequest(
            @NonNull String uri,
            @NonNull T body,
            @NonNull ParameterizedTypeReference<R> responseType
    ) {
        return webClient.put()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    Mono<Void> sendPutRequest(@NonNull String uri) {
        return webClient.put()
                .uri(uri)
                .retrieve()
                .bodyToMono(Void.class);
    }

    <T, R> Mono<R> sendPostRequest(
            @NonNull String uri,
            @NonNull T body,
            @NonNull ParameterizedTypeReference<R> responseType
    ) {
        return webClient.post()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Mono<T> sendGetRequest(ParameterizedTypeReference<T> type,
                                      String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(type);
    }
}