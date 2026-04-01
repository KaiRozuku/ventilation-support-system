package com.ipze.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Alert {
    private Long id;

    private Long transformerId;

    private String message;

    private AlertLevel level;

    private Double temperature;

    private Double voltage;

    private String timestamp;

    private Integer problemResolved;
}