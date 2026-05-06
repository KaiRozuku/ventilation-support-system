package com.ipze.dto.request;

import java.time.LocalDateTime;


public record ChatParticipantDto(

        String userId,

        String role,

        LocalDateTime joinedAt,

        LocalDateTime lastReadAt,

        boolean muted
) {}