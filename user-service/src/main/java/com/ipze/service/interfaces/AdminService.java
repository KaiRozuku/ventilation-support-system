package com.ipze.service.interfaces;


import com.ipze.dto.request.TransformerRequest;
import com.ipze.domain.mongo.Alert;
import com.ipze.domain.mongo.Transformer;

import java.util.List;
import java.util.Optional;

public interface AdminService  {

    Optional<Transformer> exportTransformer(Long id);

    List<Transformer> exportTransformersRange(Long fromId, Long toId);

    List<Transformer> exportAllTransformers();

    List<Alert> getAllErrors();

    List<Alert> getCriticalAlerts();

    List<String> exportTransformerLogs(Long id);

    void createTransformer(TransformerRequest request);

    void updateTransformer(Long id, TransformerRequest request);

    void deactivateTransformer(Long id);
}