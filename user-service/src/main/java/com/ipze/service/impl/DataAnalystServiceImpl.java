//package com.ipze.service.impl;
//
//import com.ipze.domain.mongo.Alert;
//import com.ipze.domain.mongo.Transformer;
//import com.ipze.domain.mongo.AlertLevel;
//import com.ipze.exception.TransformerNotFoundException;
//import com.ipze.service.interfaces.AlertService;
//import com.ipze.service.interfaces.DataAnalystService;
//import com.ipze.service.interfaces.OperatorService;
//import com.ipze.service.interfaces.TransformerService;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class DataAnalystServiceImpl implements DataAnalystService {
//
//    private final TransformerService transformerService;
//    private final OperatorService operatorService;
//    private final AlertService alertService;
//
//    @Override
//    public Optional<Transformer> exportTransformer(Long id) {
//        return Optional.of(operatorService.getTransformer(id)
//                .orElseThrow(TransformerNotFoundException::new));
//    }
//
//    @Override
//    public List<Transformer> exportTransformersRange(Long fromId, Long toId) {
//        return transformerService.getAll().stream()
//                .filter(t -> t.getId() >= fromId && t.getId() <= toId)
//                .toList();
//    }
//
//    @Override
//    public List<Transformer> exportAllTransformers() {
//        return transformerService.getAll()
//                .stream()
//                .toList();
//    }
//
//    @Override
//    public List<Alert> getAllErrors() {
//        return alertService.findAll().stream()
//                .toList();
//    }
//
//    @Override
//    public List<Alert> getCriticalAlerts() {
//        return alertService.findAll().stream()
//                .filter(a -> a.getLevel() == AlertLevel.CRITICAL)
//                .toList();
//    }
//
//    @Override
//    public List<String> exportTransformerLogs(Long id) {
//        return transformerService.getById(id)
//                .orElseThrow(TransformerNotFoundException::new)
//                .getDataLogs()
//                .stream()
//                .map(Object::toString)
//                .toList();
//    }
//}