package com.ipze;

import com.ipze.mongo.AlertLevel;

import java.util.UUID;

public record CreateAlertRequest(UUID transformerId, String message, AlertLevel level, Double temp, Double volt) {
}
