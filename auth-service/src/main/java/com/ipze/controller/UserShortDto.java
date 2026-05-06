package com.ipze.controller;

import java.util.UUID;

public record UserShortDto(
        UUID userId,
        String username,
        String email,
        String avatarUrl
) {}