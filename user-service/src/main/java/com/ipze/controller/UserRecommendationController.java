package com.ipze.controller;

import com.ipze.dto.RadiationMeasurementDto;
import com.ipze.dto.response.RecommendationResponse;
import com.ipze.client.interfaces.RecommendationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analytic/predict")
public class UserRecommendationController {

    private final RecommendationClient recommendationClient;

    @GetMapping("/auto")
    public Mono<ResponseEntity<RecommendationResponse>> predictLive(){
        return recommendationClient.predictLive().thenReturn(ResponseEntity.ok().build());
    }

    @PostMapping("/test")
    public Mono<ResponseEntity<RecommendationResponse>> predictTest(@RequestBody List<RadiationMeasurementDto> data){
        return recommendationClient.predictBatch(data).thenReturn(ResponseEntity.ok().build());
    }
}