package com.ipze.service.impl;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.request.TransformerRequest;
import com.ipze.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor //add real uris
public class AdminServiceImpl implements AdminService {

    private final WebClientUtils webClientUtils;

    public Mono<Void> createTransformer(TransformerRequest request) {
        return webClientUtils.sendPostRequest(
                UriComponentsBuilder
                        .fromPath("/rt")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<>(){}
        );
    }

    @Override
    public Mono<Void> updateTransformer(TransformerRequest request) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/dd")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<>() {}
                );
    }

    @Override
    public Mono<Transformer> exportTransformer(UUID uuid) {
        return webClientUtils.sendGetRequest(new ParameterizedTypeReference<>(){},
                UriComponentsBuilder
                        .fromPath("/export?uuid={uuid}")
                        .buildAndExpand(uuid)
                        .toUriString()
        );
    }

    @Override
    public Mono<Page<Transformer>> exportAllTransformers(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/fd")
                        .toUriString()
                );
    }

    @Override
    public Mono<Page<Alert>> getAllErrors(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/65fd")
                        .toUriString()
                );
    }

    @Override
    public Mono<Page<Alert>> getCriticalAlerts(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/fdy")
                        .toUriString()
        );
    }

    @Override
    public Mono<Page<String>> exportTransformerLogs(UUID uuid, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/fdg{uuid}")
                        .buildAndExpand(uuid)
                        .toUriString()
                );
    }
}