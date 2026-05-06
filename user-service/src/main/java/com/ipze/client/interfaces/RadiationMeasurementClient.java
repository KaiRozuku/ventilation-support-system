package com.ipze.client.interfaces;

import com.ipze.dto.response.RadiationMeasurementResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface RadiationMeasurementClient {

        Flux<RadiationMeasurementResponse> getAllMeasurements();

        Mono<RadiationMeasurementResponse> getMeasurementById(String id);

        Mono<Void> deleteById(String id);

        Mono<Void> deleteAll();
}