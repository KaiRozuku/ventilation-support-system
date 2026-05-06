package com.ipze.client.interfaces;

import com.ipze.dto.RadiationMeasurementDto;
import com.ipze.dto.response.RecommendationResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RecommendationClient {

    Mono<RecommendationResponse> predictLive();

    Mono<RecommendationResponse> predictBatch(List<RadiationMeasurementDto> dataSet);
}