package com.ipze.client.impl;

import com.ipze.client.interfaces.RadiationMeasurementClient;
import com.ipze.dto.response.RadiationMeasurementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'SYSTEM', 'OPERATOR', 'DATA_ANALYST')")
public class RadiationMeasurementClientImpl implements RadiationMeasurementClient {

    private final WebClientUtils webClientUtils;

    @Override
    public Flux<RadiationMeasurementResponse> getAllMeasurements(){
        return webClientUtils.sendGetRequestFlux(
                UriComponentsBuilder
                        .fromPath("/data/data/measurements")
                        .toUriString(),
                RadiationMeasurementResponse.class
        );
    }

    @Override
    public Mono<RadiationMeasurementResponse> getMeasurementById(String id){
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("/data/data/measurements/{id}")
                        .buildAndExpand(id)
                        .toUriString(),
                new ParameterizedTypeReference<>() {}
        );
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SYSTEM')")
    @Override
    public Mono<Void> deleteById(String id){
    return webClientUtils.sendDeleteRequest(
                UriComponentsBuilder
                        .fromPath("/data/data/measurements/{id}")
                        .buildAndExpand(id)
                        .toUriString(),
                Void.class
            );
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SYSTEM')")
    @Override
    public Mono<Void> deleteAll(){
        return webClientUtils.sendDeleteRequest(
                UriComponentsBuilder
                        .fromPath("/data/data/measurements")
                        .toUriString(),
                Void.class
        );
    }
}
