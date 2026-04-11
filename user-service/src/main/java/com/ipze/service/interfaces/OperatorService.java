package com.ipze.service.interfaces;


import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.TransformerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface OperatorService {

    Page<Transformer> getAllTransformers(Pageable pageable);

    Optional<Transformer> getTransformer(UUID uuid);

    TransformerStatus getTransformerStatus(UUID uuid);

    Page<Alert> getTransformerAlerts(UUID uuid, Pageable pageable);

    Page<TransformerStatus> getAllTransformersStatus(Pageable pageable);

    void addErrorProcessing(UUID uuid);
}