package com.ipze.service;

import com.ipze.mongo.Alert;
import com.ipze.mongo.AlertLevel;

import java.util.List;

public interface AlertService {

    void createAlert();

    void createOperatorNote(Long id);

    List<Alert> findAll();

    List<Alert> getAlertsByTransformerId(Long transformerId);
}

