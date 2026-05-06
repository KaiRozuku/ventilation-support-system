package com.ipze.dto.response;

public record ModelHealthResponse(
        boolean trained,
        int featuresCount,
        long datasetSize
) {}