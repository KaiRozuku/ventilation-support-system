package com.ipze.controller;

import com.ipze.dto.response.ProducerParamsResponse;
import com.ipze.dto.response.UpdateProducerParamsRequest;
import com.ipze.client.interfaces.ProducerRuntimeConfigClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/producer")
public class ProducerRuntimeConfigController {

    private final ProducerRuntimeConfigClient producerRuntimeConfigClient;

    @PatchMapping("/config")
    public Mono<ResponseEntity<ProducerParamsResponse>> updateParams(
            @RequestBody UpdateProducerParamsRequest request) {

        return producerRuntimeConfigClient.updateParams(request)
                .map(ResponseEntity::ok);
    }
}