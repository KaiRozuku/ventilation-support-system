package com.ipze.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * switch {@code String uri} to {@code URI uri}
 */

@Component
@RequiredArgsConstructor
public class WebClientUtils {

    private final WebClient webClient;

    public <T, R> Mono<R> sendPutRequest(
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

    public Mono<Void> sendPutRequest(@NonNull String uri) {
        return webClient.put()
                .uri(uri)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public <T, R> Mono<R> sendPostRequest(
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

    public <T> Mono<T> sendGetRequest(
            @NonNull ParameterizedTypeReference<T> type,
            @NonNull String uri
    ) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(type);
    }
}