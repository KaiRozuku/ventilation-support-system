package com.ipze.client.interfaces;

import com.ipze.dto.MetricType;
import com.ipze.dto.response.FullMetricsResponse;
import com.ipze.dto.response.ModelHealthResponse;
import reactor.core.publisher.Mono;

public interface AnalyticMetricsClient {

    Mono<FullMetricsResponse> getFullMetrics();

    Mono<Object> getMetric(MetricType type);

    Mono<ModelHealthResponse> getHealth();
}