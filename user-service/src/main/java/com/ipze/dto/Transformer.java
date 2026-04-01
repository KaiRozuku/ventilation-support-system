package com.ipze.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Transformer {

    private Long id;

    private String manufacturer;

    private String modelType;

    private Double ratedPowerKVA;

    private Integer primaryVoltageKV;

    private Integer secondaryVoltageKV;

    private Double frequencyHz;

    private Boolean transformerCondition;

    private Boolean remoteMonitoring;

    private Double currentPower;

    private Double currentTemperature;

    private Double currentVoltage;

    private TransformerStatus status;

    private List<Map<String, Object>> dataLogs = new ArrayList<>(); //?

}