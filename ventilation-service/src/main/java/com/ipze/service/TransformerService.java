package com.ipze.service;


import com.ipze.dto.request.TransformerRequest;
import com.ipze.domain.mongo.Transformer;
import com.ipze.domain.mongo.TransformerStatus;

import java.util.List;
import java.util.Optional;

public interface TransformerService {

    Optional<Transformer> getById(Long id);

    List<Transformer> getAll();

    void save(Transformer transformer);

    TransformerStatus checkStatus(Long transformerId);

    void updateData(Long id, Double power, Double temperature, Double voltage);

    void updateStatus(Long transformerId, Double temperature, Double voltage);

    Transformer create(TransformerRequest request);

    void deactivate(Long id);

    Transformer update(Long id, TransformerRequest request);
}