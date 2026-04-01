package com.ipze.dto.request;

import java.util.UUID;

public record TransformerRequest(
        UUID uuid,
        String manufacturer,
        String modelType,
        Double ratedPowerKVA,
        Integer primaryVoltageKV,
        Integer secondaryVoltageKV,
        Double frequencyHz,
        Boolean transformerCondition,
        Boolean remoteMonitoring
) {}