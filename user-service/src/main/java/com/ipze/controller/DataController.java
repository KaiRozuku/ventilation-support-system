package com.ipze.controller;


import com.ipze.client.interfaces.RadiationMeasurementClient;
import com.ipze.dto.response.RadiationMeasurementResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final RadiationMeasurementClient radiationMeasurementClient;
    private final WebClient webClient;

    @GetMapping("/measurements")
    public Flux<RadiationMeasurementResponse> getAll() {
        return radiationMeasurementClient.getAllMeasurements();
    }

    @GetMapping("/measurements/{id}")
    public Mono<RadiationMeasurementResponse> getById(@PathVariable String id) {
        return radiationMeasurementClient.getMeasurementById(id);
    }

    @DeleteMapping("/measurements/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return radiationMeasurementClient.deleteById(id);
    }

    @DeleteMapping("/measurements")
    public Mono<Void> deleteAll() {
        return radiationMeasurementClient.deleteAll();
    }

    @GetMapping("/debug")
    public Mono<String> debug() {
        return webClient.get()
                .uri("/data/measurements")
                .retrieve()
                .bodyToMono(String.class);
    }
}