package com.ipze.service.interfaces;

import com.ipze.domain.mongo.Alert;
import com.ipze.domain.mongo.AlertLevel;

import java.util.List;

public interface AlertService {

    void createAndSaveAlert(Long transformerId, String message, AlertLevel level, Double temp, Double volt);

    void createOperatorNote(Long id);

    List<Alert> findAll();

    List<Alert> getAlertsByTransformerId(Long transformerId);
}