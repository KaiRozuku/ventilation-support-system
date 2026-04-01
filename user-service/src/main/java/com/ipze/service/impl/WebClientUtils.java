package com.ipze.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
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
            @NonNull ParameterizedTypeReference<R> responseType,
            @NonNull HttpServletRequest request
    ) {
        return webClient.put()
                .uri(uri)
                .headers(headers -> copyHeaders(request, headers))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    Mono<Void> sendPutRequest(
            @NonNull String uri,
            @NonNull HttpServletRequest request
    ) {
        return webClient.put()
                .uri(uri)
                .headers(headers -> copyHeaders(request, headers))
                .retrieve()
                .bodyToMono(Void.class);
    }

    <T, R> Mono<R> sendPostRequest(
            @NonNull String uri,
            @NonNull T body,
            @NonNull ParameterizedTypeReference<R> responseType,
            @NonNull HttpServletRequest request
    ) {
        return webClient.post()
                .uri(uri)
                .headers(headers -> copyHeaders(request, headers))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T> Mono<T> sendGetRequest(ParameterizedTypeReference<T> type,
                                      String uri,
                                      HttpServletRequest httpServletRequest) {
        return webClient.get()
                .uri(uri)
                .headers(headers -> copyHeaders(httpServletRequest, headers))
                .retrieve()
                .bodyToMono(type);
    }

    private void copyHeaders(HttpServletRequest request, HttpHeaders headers) {
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = request.getHeader("X-User-ID");
        String username = request.getHeader("X-User-Name");
        String roles = request.getHeader("X-User-Roles");

        if (auth != null) headers.set(HttpHeaders.AUTHORIZATION, auth);
        if (userId != null) headers.set("X-User-ID", userId);
        if (username != null) headers.set("X-User-Name", username);
        if (roles != null) headers.set("X-User-Roles", roles);
    }
}