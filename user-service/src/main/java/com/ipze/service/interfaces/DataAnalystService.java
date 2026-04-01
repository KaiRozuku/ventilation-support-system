package com.ipze.service.interfaces;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DataAnalystService {

    Optional<Transformer> exportTransformer(UUID uuid, Pageable pageable, HttpServletRequest httpServletRequest);

    Page<Transformer> exportAllTransformers(Pageable pageable, HttpServletRequest httpServletRequest);

    Page<Alert> getAllErrors(Pageable pageable, HttpServletRequest httpServletRequest);

    Page<Alert> getCriticalAlerts(Pageable pageable, HttpServletRequest httpServletRequest);

    Page<String> exportTransformerLogs(UUID uuid, Pageable pageable, HttpServletRequest httpServletRequest);
}