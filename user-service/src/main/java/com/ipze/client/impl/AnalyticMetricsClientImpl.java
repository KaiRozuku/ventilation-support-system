package com.ipze.client.impl;

import com.ipze.client.interfaces.AnalyticMetricsClient;
import com.ipze.dto.MetricType;
import com.ipze.dto.response.FullMetricsResponse;
import com.ipze.dto.response.ModelHealthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'DATA_ANALYST', 'SYSTEM')")
public class AnalyticMetricsClientImpl implements AnalyticMetricsClient {

    private final WebClient webClient;

    @Override
    public Mono<FullMetricsResponse> getFullMetrics() {
        return webClient.get()
                .uri("/metrics/")
                .retrieve()
                .bodyToMono(FullMetricsResponse.class);
    }

    @Override
    public Mono<Object> getMetric(MetricType type) {
        return webClient.get()
                .uri("/metrics/{metric}", type.name().toLowerCase())
                .retrieve()
                .bodyToMono(Object.class);
    }

    @Override
    public Mono<ModelHealthResponse> getHealth() {
        return webClient.get()
                .uri("/metrics/health")
                .retrieve()
                .bodyToMono(ModelHealthResponse.class);
    }
}