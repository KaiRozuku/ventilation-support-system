package com.ipze.service.impl;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.service.interfaces.DataAnalystService;
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
public class DataAnalystServiceImpl implements DataAnalystService {

    private final WebClientUtils webClientUtils;

    @Override
    public Optional<Transformer> exportTransformer(UUID uuid,
                                                   Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Optional<Transformer>>() {},
                UriComponentsBuilder
                        .fromPath("/de?uuid=")
                        .buildAndExpand(uuid)
                        .toUriString()
        ).block();
    }

    @Override
    public Page<Transformer> exportAllTransformers(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Transformer>>() {},
                UriComponentsBuilder
                        .fromPath("/ded")
                        .toUriString()
        ).block();
    }

    @Override
    public Page<Alert> getAllErrors(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Alert>>() {},
                UriComponentsBuilder
                        .fromPath("/ss")
                        .toUriString()
        ).block();
    }

    @Override
    public Page<Alert> getCriticalAlerts(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Alert>>() {},
                UriComponentsBuilder
                        .fromPath("/aw")
                        .toUriString()
        ).block();
    }

    @Override
    public Page<String> exportTransformerLogs(UUID uuid, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<String>>() {},
                UriComponentsBuilder
                        .fromPath("/ff")
                        .toUriString()
        ).block();
    }
}