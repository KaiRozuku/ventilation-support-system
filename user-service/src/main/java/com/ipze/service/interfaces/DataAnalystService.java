package com.ipze.service.interfaces;

import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DataAnalystService {

    Optional<Transformer> exportTransformer(UUID uuid, Pageable pageable);

    Page<Transformer> exportAllTransformers(Pageable pageable);

    Page<Alert> getAllErrors(Pageable pageable);

    Page<Alert> getCriticalAlerts(Pageable pageable);

    Page<String> exportTransformerLogs(UUID uuid, Pageable pageable);
}