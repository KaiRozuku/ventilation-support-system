package com.ipze.security;

import com.ipze.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatSecurity {

    private final ChatRoomRepository chatRoomRepository;

    public boolean isParticipant(String roomId) {
        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return chatRoomRepository
                .existsByIdAndParticipantIdsContains(roomId, userId);
    }
}