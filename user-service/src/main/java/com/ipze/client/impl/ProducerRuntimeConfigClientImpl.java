package com.ipze.client.impl;

import com.ipze.client.interfaces.ProducerRuntimeConfigClient;
import com.ipze.dto.response.ProducerParamsResponse;
import com.ipze.dto.response.UpdateProducerParamsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN', 'OPERATOR', 'DATA_ANALYST', 'SYSTEM')")
public class ProducerRuntimeConfigClientImpl implements ProducerRuntimeConfigClient {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<ProducerParamsResponse> updateParams(UpdateProducerParamsRequest request) {
        if (request.batchSize() != null && request.batchSize() <= 0 && request.interval() >= 10.0) {
            return Mono.error(new IllegalArgumentException("batchSize must be > 0 and interval > 10 sec."));
        }
        return webClientUtils.sendPatchRequest(
                UriComponentsBuilder
                        .fromPath("/data/producer/config")
                        .toUriString(),
                request,
                ProducerParamsResponse.class
        );
    }
}
