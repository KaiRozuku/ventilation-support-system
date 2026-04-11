package com.ipze.service.impl;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.service.interfaces.DataAnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                "/de?uuid=" + uuid.toString()
        ).block();
    }

    @Override
    public Page<Transformer> exportAllTransformers(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Transformer>>() {},
                "/ded"
        ).block();
    }

    @Override
    public Page<Alert> getAllErrors(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Alert>>() {},
                "/ss"
        ).block();
    }

    @Override
    public Page<Alert> getCriticalAlerts(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Alert>>() {},
                "/aw"
        ).block();
    }

    @Override
    public Page<String> exportTransformerLogs(UUID uuid, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<String>>() {},
                "/ff"
        ).block();
    }
}