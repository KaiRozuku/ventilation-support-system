package com.ipze.client.impl;

import com.ipze.dto.response.RadiationMeasurementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FastApiClient {

    private final WebClientUtils webClientUtils;

    public Flux<RadiationMeasurementResponse> getAllMeasurements() {
        return webClientUtils.sendGetRequestFlux(
                UriComponentsBuilder
                        .fromPath("/data/measurements")
                        .toUriString(),
                RadiationMeasurementResponse.class
        );
    }

    public Mono<RadiationMeasurementResponse> getById(String id) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("/measurements/{id}")
                        .buildAndExpand(id)
                        .toUriString(),
                new ParameterizedTypeReference<>(){}
        );
    }

    public Mono<Void> deleteById(String id) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("/measurements/{id}")
                        .buildAndExpand(id)
                        .toUriString(),
                new ParameterizedTypeReference<>() {});
    }

    public Mono<Void> deleteAll() {
        return webClientUtils.sendDeleteRequest(
                UriComponentsBuilder
                        .fromPath("/data/measurements")
                        .toUriString(),
                Void.class);
    }
}