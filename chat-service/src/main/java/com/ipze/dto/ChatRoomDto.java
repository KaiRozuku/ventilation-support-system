package com.ipze.dto;

import com.ipze.enums.ChatType;

import java.time.LocalDateTime;
import java.util.List;


public record ChatRoomDto(
        String id,
        String name,
        ChatType chatType,
        String createdBy,
        LocalDateTime createdAt,
        List<String> participantIds,
        List<ChatParticipantDto> participants
) {}