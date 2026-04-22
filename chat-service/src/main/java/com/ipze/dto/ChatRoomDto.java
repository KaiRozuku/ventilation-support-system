package com.ipze.dto;

import com.ipze.enums.ChatType;

import java.time.LocalDateTime;
import java.util.List;

public record ChatRoomDto (
        String id,
        List<ChatParticipantDto> participants,
        ChatType chatType,
        String name,
        String createdBy,
        LocalDateTime createdAt) {
}