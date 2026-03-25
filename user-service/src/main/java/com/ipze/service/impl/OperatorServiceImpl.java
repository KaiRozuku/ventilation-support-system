//package com.ipze.service.impl;
//
//import com.ipze.domain.mongo.Alert;
//import com.ipze.domain.mongo.Transformer;
//import com.ipze.domain.mongo.TransformerStatus;
//import com.ipze.exception.TransformerNotFoundException;
//import com.ipze.service.AlertService;
//import com.ipze.service.interfaces.OperatorService;
//import com.ipze.service.TransformerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class OperatorServiceImpl implements OperatorService {
//
//    private final TransformerService transformerService;
//    private final AlertService alertService;
//
//    @Override
//    public List<Transformer> getAllTransformers() {
//        return transformerService.getAll();
//    }
//
//    @Override
//    public Optional<Transformer> getTransformer(Long id) {
//        return Optional.ofNullable(transformerService.getById(id)
//                        .orElseThrow(TransformerNotFoundException::new));
//    }
//
//    @Override
//    public TransformerStatus getTransformerStatus(Long id) {
//        return transformerService
//                .getById(id)
//                .map(Transformer::getStatus)
//                .orElseThrow(TransformerNotFoundException::new);
//    }
//
//    @Override
//    public List<Alert> getTransformerAlerts(Long id) {
//        return alertService.getAlertsByTransformerId(id);
//    }
//
//    @Override
//    public List<TransformerStatus> getAllTransformersStatus() {
//        return transformerService.getAll()
//                .stream()
//                .map(Transformer::getStatus)
//                .toList();
//    }
//
//    @Override
//    public Alert addErrorProcessing(Long transformerId) {
//        alertService.createOperatorNote(transformerId);
//        return null;
//    }
//}