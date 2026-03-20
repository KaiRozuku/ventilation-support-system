package com.ipze.service.interfaces;


import com.ipze.domain.mongo.Alert;
import com.ipze.domain.mongo.Transformer;
import com.ipze.domain.mongo.TransformerStatus;

import java.util.List;
import java.util.Optional;

public interface OperatorService {

    List<Transformer> getAllTransformers();

    Optional<Transformer> getTransformer(Long id);

    TransformerStatus getTransformerStatus(Long id);

    List<Alert> getTransformerAlerts(Long id);

    List<TransformerStatus> getAllTransformersStatus();

    Alert addErrorProcessing(Long transformerId);
}