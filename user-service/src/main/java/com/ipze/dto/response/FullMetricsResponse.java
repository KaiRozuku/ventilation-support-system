package com.ipze.dto.response;

import java.util.List;
import java.util.Map;

public record FullMetricsResponse(
        double accuracy,
        Map<String, Object> classificationReport,
        List<List<Integer>> confusionMatrix,
        List<FeatureImportanceItem> featureImportance
) {}