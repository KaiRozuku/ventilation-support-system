package com.ipze.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Alert {
    private UUID uuid;

    private Long transformerId;

    private String message;

    private AlertLevel level;

    private Double temperature;

    private Double voltage;

    private String timestamp;

    private Integer problemResolved;
}