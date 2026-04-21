package com.ipze.service.impl;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.TransformerStatus;
import com.ipze.dto.response.PageResponse;
import com.ipze.service.interfaces.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {
    private final WebClientUtils webClientUtils;

    @Override
    public Mono<PageResponse<Transformer>> getAllTransformers(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {
                },
                UriComponentsBuilder
                        .fromPath("/gf")
                        .toUriString()
        );
    }

    @Override
    public Mono<Transformer> getTransformer(UUID uuid) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {
                },
                UriComponentsBuilder
                        .fromPath("/g{uuid}")
                        .buildAndExpand(uuid)
                        .toUriString()
        );
    }

    @Override
    public Mono<TransformerStatus> getTransformerStatus(UUID uuid) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {
                },
                UriComponentsBuilder
                        .fromPath("/fdg{uuid}")
                        .buildAndExpand(uuid)
                        .toUriString()
        );
    }

    @Override
    public Mono<PageResponse<Alert>> getTransformerAlerts(UUID uuid, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {
                },
                UriComponentsBuilder
                        .fromPath("/fgf")
                        .toUriString()
        );
    }

    @Override
    public Mono<PageResponse<TransformerStatus>> getAllTransformersStatus(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {
                },
                UriComponentsBuilder
                        .fromPath("/dg")
                        .toUriString()
        );
    }

    @Override
    public Mono<Void> addErrorProcessing(UUID uuid) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/dj{uuid}")
                        .buildAndExpand(uuid)
                        .toUriString()
        );
    }
}