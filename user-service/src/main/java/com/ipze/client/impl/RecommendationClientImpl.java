package com.ipze.client.impl;

import com.ipze.client.interfaces.RecommendationClient;
import com.ipze.dto.RadiationMeasurementDto;
import com.ipze.dto.response.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMIN', 'DATA_ANALYST', 'SYSTEM')")
public class RecommendationClientImpl implements RecommendationClient {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<RecommendationResponse> predictLive() {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("/analytic/analytic/predict/")
                        .toUriString(),
                new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public Mono<RecommendationResponse> predictBatch(List<RadiationMeasurementDto> dataSet) {
        return webClientUtils.sendPostRequest(
                UriComponentsBuilder
                        .fromPath("/analytic/analytic/predict/test")
                        .toUriString(),
                dataSet,
                new ParameterizedTypeReference<>() {});
    }
}