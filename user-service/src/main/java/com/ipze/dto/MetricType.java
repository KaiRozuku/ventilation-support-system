package com.ipze.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum MetricType {
    ACCURACY("accuracy"),
    REPORT("report"),
    CONFUSION("confusion"),
    FEATURE("feature");

    private final String path;
}