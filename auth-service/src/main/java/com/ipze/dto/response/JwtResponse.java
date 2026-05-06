package com.ipze.dto.response;

import lombok.Builder;

@Builder
public record JwtResponse(String accessToken, String refreshToken) {}