package com.ipze.client.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * switch {@code String uri} to {@code URI uri}
 */

@Component
@RequiredArgsConstructor
public class WebClientUtils {

    private final WebClient webClient;

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

    public <T> Mono<T> sendGetRequest(
            @NonNull String uri,
            @NonNull ParameterizedTypeReference<T> responseType
    ) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Flux<T> sendGetRequestFlux(
            @NonNull String uri,
            @NonNull Class<T> elementType
    ) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(elementType);
    }

    public <T> Mono<T> sendDeleteRequest(
            @NonNull String uri,
            @NonNull Class<T> responseType
    ){
        return webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T, R> Mono<T> sendPatchRequest(
            @NonNull String uri,
            @NonNull R body,
            @NonNull Class<T> responseType
    ) {
        return webClient.patch()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }
}