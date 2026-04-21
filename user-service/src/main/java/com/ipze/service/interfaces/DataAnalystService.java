package com.ipze.service.interfaces;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DataAnalystService {

    Mono<Transformer> exportTransformer(UUID uuid, Pageable pageable);

    Mono<PageResponse<Transformer>> exportAllTransformers(Pageable pageable);

    Mono<PageResponse<Alert>> getAllErrors(Pageable pageable);

    Mono<PageResponse<Alert>> getCriticalAlerts(Pageable pageable);

    Mono<PageResponse<String>> exportTransformerLogs(UUID uuid, Pageable pageable);
}