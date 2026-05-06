package com.ipze.dto;

import com.ipze.enums.ParticipantRole;

import java.time.LocalDateTime;

public record ChatParticipantDto(
        String userId,
        String username,
        String email,
        String avatarUrl,
        ParticipantRole role,
        LocalDateTime joinedAt
) {}