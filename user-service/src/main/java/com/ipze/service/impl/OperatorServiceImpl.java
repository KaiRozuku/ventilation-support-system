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
                "/gf"
        ).block();
    }

    @Override
    public Optional<Transformer> getTransformer(UUID uuid) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Optional<Transformer>>() {},
                "/g" + uuid.toString()
        ).block();
    }

    @Override
    public TransformerStatus getTransformerStatus(UUID uuid) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<TransformerStatus>() {},
                "/fdg" + uuid.toString()
        ).block();
    }

    @Override
    public Page<Alert> getTransformerAlerts(UUID uuid, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Alert>>() {},
                "/fgf"
        ).block();
    }

    @Override
    public Page<TransformerStatus> getAllTransformersStatus(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<TransformerStatus>>() {},
                "/dg"
        ).block();
    }

    @Override
    public void addErrorProcessing(UUID uuid) {
        webClientUtils.sendPutRequest(
                "/dj" + uuid.toString()
        ).block();
    }
}