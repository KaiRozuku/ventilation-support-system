package com.ipze.service.interfaces;


import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.TransformerStatus;
import com.ipze.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OperatorService {

    Mono<PageResponse<Transformer>> getAllTransformers(Pageable pageable);

    Mono<Transformer> getTransformer(UUID uuid);

    Mono<TransformerStatus> getTransformerStatus(UUID uuid);

    Mono<PageResponse<Alert>> getTransformerAlerts(UUID uuid, Pageable pageable);

    Mono<PageResponse<TransformerStatus>> getAllTransformersStatus(Pageable pageable);

    Mono<Void> addErrorProcessing(UUID uuid);
}