//package com.ipze.service.impl;
//
//import com.ipze.dto1.request.AlertRequest;
//import com.ipze.domain.mongo.Alert;
//import com.ipze.domain.mongo.AlertLevel;
//import com.ipze.repository.mongo.AlertRepository;
//import com.ipze.service.interfaces.AlertService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AlertServiceImpl implements AlertService {
//
//    private final AlertRepository alertRepository;
//
//    private Long getNextId() {
//        return alertRepository.findTopByOrderByIdDesc()
//                .map(Alert::getId)
//                .map(id -> id + 1)
//                .orElse(1L);
//    }
//
//    public void createAndSaveAlert(Long transformerId, String message, AlertLevel level, Double temp, Double volt) {
//        create(new AlertRequest(transformerId, message, null, temp, volt));
//    }
//
//    @Override
//    public Alert create(AlertRequest request) {
//        return alertRepository.save(
//                new Alert(
//                        getNextId(),
//                        request.transformerId(),
//                        request.message(),
//                        null,
//                        request.temperature(),
//                        request.voltage(),
//                        LocalDateTime.now().toString(),
//                        null
//                ));
//    }
//
//    @Override
//    public void createOperatorNote(Long id) {
//        Alert alert = alertRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Alert not found: " + id));
//
//        alert.setProblemResolved(1);
//        alertRepository.save(alert);
//    }
//    @Override
//    public List<Alert> findAll() {
//        return alertRepository.findAll();
//    }
//
//    @Override
//    public List<Alert> getAlertsByTransformerId(Long transformerId) {
//        return alertRepository.findByTransformerId(transformerId);
//    }
//}