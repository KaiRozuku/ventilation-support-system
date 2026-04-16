package com.ipze.service.impl;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.TransformerStatus;
import com.ipze.service.interfaces.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {
    private final WebClientUtils webClientUtils;

    @Override
    public Page<Transformer> getAllTransformers(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Transformer>>() {},
                UriComponentsBuilder
                        .fromPath("/gf")
                        .toUriString()
        ).block();
    }

    @Override
    public Optional<Transformer> getTransformer(UUID uuid) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Optional<Transformer>>() {},
                UriComponentsBuilder
                        .fromPath("/g{uuid}")
                        .buildAndExpand(uuid)
                        .toUriString()
        ).block();
    }

    @Override
    public TransformerStatus getTransformerStatus(UUID uuid) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<TransformerStatus>() {},
                UriComponentsBuilder
                        .fromPath("/fdg{uuid}")
                        .buildAndExpand(uuid)
                        .toUriString()
        ).block();
    }

    @Override
    public Page<Alert> getTransformerAlerts(UUID uuid, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Alert>>() {},
                UriComponentsBuilder
                        .fromPath("/fgf")
                        .toUriString()
        ).block();
    }

    @Override
    public Page<TransformerStatus> getAllTransformersStatus(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<TransformerStatus>>() {},
                UriComponentsBuilder
                        .fromPath("/dg")
                        .toUriString()
        ).block();
    }

    @Override
    public void addErrorProcessing(UUID uuid) {
        webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/dj{uuid}")
                        .buildAndExpand(uuid)
                        .toUriString()
        ).block();
    }
}