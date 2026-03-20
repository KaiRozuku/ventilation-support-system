package com.ipze.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(String username, String accessToken, String refreshToken) {}