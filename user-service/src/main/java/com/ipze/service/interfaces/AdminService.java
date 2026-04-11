package com.ipze.service.interfaces;


import com.ipze.dto.Alert;
import com.ipze.dto.request.TransformerRequest;
import com.ipze.dto.Transformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AdminService  {

    Mono<Transformer> exportTransformer(UUID uuid);

    Mono<Page<Transformer>> exportAllTransformers(Pageable pageable);

    Mono<Page<Alert>> getAllErrors(Pageable pageable);

    Mono<Page<Alert>> getCriticalAlerts(Pageable pageable);

    Mono<Page<String>> exportTransformerLogs(UUID uuid, Pageable pageable);

    Mono<Void> createTransformer(TransformerRequest request);

    Mono<Void> updateTransformer(TransformerRequest request);
}