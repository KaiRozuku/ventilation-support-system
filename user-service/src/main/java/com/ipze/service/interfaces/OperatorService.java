package com.ipze.service.interfaces;


import com.ipze.dto.Alert;
import com.ipze.dto.Transformer;
import com.ipze.dto.TransformerStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface OperatorService {

    Page<Transformer> getAllTransformers(Pageable pageable, HttpServletRequest httpServletRequest);

    Optional<Transformer> getTransformer(UUID uuid, HttpServletRequest httpServletRequest);

    TransformerStatus getTransformerStatus(UUID uuid, HttpServletRequest httpServletRequest);

    Page<Alert> getTransformerAlerts(UUID uuid, Pageable pageable, HttpServletRequest httpServletRequest);

    Page<TransformerStatus> getAllTransformersStatus(Pageable pageable, HttpServletRequest httpServletRequest);

    void addErrorProcessing(UUID uuid, HttpServletRequest httpServletRequest);
}