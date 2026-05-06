package com.ipze.controller;

import com.ipze.dto.MetricType;
import com.ipze.dto.response.FullMetricsResponse;
import com.ipze.dto.response.ModelHealthResponse;
import com.ipze.client.interfaces.AnalyticMetricsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class UserAnalyticMetricsController {

    private final AnalyticMetricsClient metricsClient;

    @GetMapping
    public Mono<FullMetricsResponse> fullMetrics() {
        return metricsClient.getFullMetrics();
    }

    @GetMapping("/{metric}")
    public Mono<Object> metric(@PathVariable MetricType metric) {
        return metricsClient.getMetric(metric);
    }

    @GetMapping("/health")
    public Mono<ModelHealthResponse> health() {
        return metricsClient.getHealth();
    }
}