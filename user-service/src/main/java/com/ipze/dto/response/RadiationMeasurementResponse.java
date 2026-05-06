package com.ipze.dto.response;


public record RadiationMeasurementResponse(
        String oid,

        double fullWeightKg,
        double sampleWeightKg,

        double cs137BqKg,
        double sr90BqKg,
        double k40BqKg,

        double ra226BqKg,
        double th232BqKg,

        double cs137KbqM2,
        double sr90KbqM2,

        double ratioCsSr

) {}