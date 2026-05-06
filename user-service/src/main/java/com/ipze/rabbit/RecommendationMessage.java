package com.ipze.rabbit;

import lombok.Data;

import java.util.Map;

@Data
public class RecommendationMessage {

    private Map<String, String> prediction;
    private String recommendation;
}
