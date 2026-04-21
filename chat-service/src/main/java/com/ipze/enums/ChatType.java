package com.ipze.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ChatType {
    PRIVATE(false),
    GROUP(true);

    private final boolean group;
}