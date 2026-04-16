package com.ipze.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ChatRoomDto (
        String id,
        List<String> participantIds,
        ChatType chatType,
        String name,
        String createdBy,
        LocalDateTime createdAt) {
}