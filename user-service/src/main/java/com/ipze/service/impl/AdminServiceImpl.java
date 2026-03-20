//package com.ipze.service.impl;
//
//import com.ipze.domain.mongo.Alert;
//import com.ipze.domain.mongo.Transformer;
//import com.ipze.dto1.request.TransformerRequest;
//import com.ipze.exception.TransformerNotFoundException;
//import com.ipze.service.interfaces.AdminService;
//import com.ipze.service.interfaces.AlertService;
//import com.ipze.service.interfaces.OperatorService;
//import com.ipze.service.interfaces.TransformerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class AdminServiceImpl implements AdminService {
//    private final TransformerService transformerService;
//    private final AlertService alertService;
//    private final OperatorService operatorService;
//
//    public void createTransformer(TransformerRequest request) {
//        transformerService.create(request);
//    }
//
//    @Override
//    public void updateTransformer(Long id, TransformerRequest request) {
//        transformerService.update(id, request);
//    }
//
//    @Override
//    public void deactivateTransformer(Long id) {
//        transformerService.deactivate(id);
//    }
//
//    @Override
//    public Optional<Transformer> exportTransformer(Long id) {
//        return operatorService.getTransformer(id);
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
//        return operatorService.getAllTransformers();
//    }
//
//    @Override
//    public List<Alert> getAllErrors() {
//        return alertService.findAll();
//    }
//
//    @Override
//    public List<Alert> getCriticalAlerts() {
//        return alertService.findAll();
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