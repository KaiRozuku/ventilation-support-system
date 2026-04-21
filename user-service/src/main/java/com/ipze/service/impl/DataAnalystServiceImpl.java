package com.ipze.service.impl;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.response.PageResponse;
import com.ipze.service.interfaces.DataAnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataAnalystServiceImpl implements DataAnalystService {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<Transformer> exportTransformer(UUID uuid,
                                               Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/de?uuid=")
                        .buildAndExpand(uuid)
                        .toUriString()
        );
    }

    @Override
    public Mono<PageResponse<Transformer>> exportAllTransformers(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/ded")
                        .toUriString()
        );
    }

    @Override
    public Mono<PageResponse<Alert>> getAllErrors(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/ss")
                        .toUriString()
        );
    }

    @Override
    public Mono<PageResponse<Alert>> getCriticalAlerts(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/aw")
                        .toUriString()
        );
    }

    @Override
    public Mono<PageResponse<String>> exportTransformerLogs(UUID uuid, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/ff")
                        .toUriString()
        );
    }
}