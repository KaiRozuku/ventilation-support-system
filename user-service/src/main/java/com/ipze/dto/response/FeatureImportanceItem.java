package com.ipze.dto.response;

public record FeatureImportanceItem(
        String feature,
        double importance
) {}