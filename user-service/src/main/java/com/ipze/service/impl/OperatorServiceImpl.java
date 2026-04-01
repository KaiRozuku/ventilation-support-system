package com.ipze.service.impl;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.TransformerStatus;
import com.ipze.service.interfaces.OperatorService;
import jakarta.servlet.http.HttpServletRequest;
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
    public Page<Transformer> getAllTransformers(Pageable pageable, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Transformer>>() {},
                "/gf",
                httpServletRequest
        ).block();
    }

    @Override
    public Optional<Transformer> getTransformer(UUID uuid, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Optional<Transformer>>() {},
                "/g" + uuid.toString(),
                httpServletRequest
        ).block();
    }

    @Override
    public TransformerStatus getTransformerStatus(UUID uuid, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<TransformerStatus>() {},
                "/fdg" + uuid.toString(),
                httpServletRequest
        ).block();
    }

    @Override
    public Page<Alert> getTransformerAlerts(UUID uuid, Pageable pageable, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Alert>>() {},
                "/fgf",
                httpServletRequest
        ).block();
    }

    @Override
    public Page<TransformerStatus> getAllTransformersStatus(Pageable pageable, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<TransformerStatus>>() {},
                "/dg",
                httpServletRequest
        ).block();
    }

    @Override
    public void addErrorProcessing(UUID uuid, HttpServletRequest httpServletRequest) {
        webClientUtils.sendPutRequest(
                "/dj" + uuid.toString(),
                httpServletRequest
        ).block();
    }
}