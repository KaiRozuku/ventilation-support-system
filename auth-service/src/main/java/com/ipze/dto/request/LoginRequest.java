package com.ipze.dto.request;

public record LoginRequest(
        String email,
        String password
        ) {}