package com.ipze.service.impl;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.request.TransformerRequest;
import com.ipze.service.interfaces.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor //add real uris
public class AdminServiceImpl implements AdminService {
    private final WebClientUtils webClientUtils;

    public void createTransformer(TransformerRequest request, HttpServletRequest httpServletRequest) {
        webClientUtils.sendPostRequest("/rt",
                request,
                new ParameterizedTypeReference<TransformerRequest>(){},
                httpServletRequest
        ).block();
    }

    @Override
    public void updateTransformer(TransformerRequest request, HttpServletRequest httpServletRequest) {
        webClientUtils.sendPutRequest("/dd",
                request,
                new ParameterizedTypeReference<TransformerRequest>() {},
                httpServletRequest
                ).block();
    }

    @Override
    public void deactivateTransformer(UUID uuid, HttpServletRequest httpServletRequest) {
        webClientUtils.sendPutRequest("/gh" + uuid.toString(),
                httpServletRequest
        ).block();
    }

    @Override
    public Transformer exportTransformer(UUID uuid, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(new ParameterizedTypeReference<Transformer>(){},
                "/export?uuid=" + uuid.toString(),
                httpServletRequest).block();
    }

    @Override
    public Page<Transformer> exportAllTransformers(Pageable pageable, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Transformer>>() {},
                "/fd",
                httpServletRequest
                ).block();
    }

    @Override
    public Page<Alert> getAllErrors(Pageable pageable, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Alert>>() {},
                "/all",
                httpServletRequest
                ).block();
    }

    @Override
    public Page<Alert> getCriticalAlerts(Pageable pageable, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<Alert>>() {},
                "/fgd",
                httpServletRequest
        ).block();
    }

    @Override
    public Page<String> exportTransformerLogs(UUID uuid, Pageable pageable, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<String>>() {},
                "/fdg" + uuid.toString(),
                httpServletRequest
                ).block();
    }
}