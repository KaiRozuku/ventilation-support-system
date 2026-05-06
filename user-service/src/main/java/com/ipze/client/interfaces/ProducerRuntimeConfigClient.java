package com.ipze.client.interfaces;

import com.ipze.dto.response.ProducerParamsResponse;
import com.ipze.dto.response.UpdateProducerParamsRequest;
import reactor.core.publisher.Mono;

public interface ProducerRuntimeConfigClient {

    Mono<ProducerParamsResponse> updateParams(
            UpdateProducerParamsRequest request
    );
}