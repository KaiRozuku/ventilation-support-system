package com.ipze.security;

import com.ipze.enums.ParticipantRole;
import com.ipze.repository.ChatParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatSecurityService {

    private final ChatParticipantRepository chatParticipantRepository;

    public boolean isChatAdmin(String chatId, String userId) {
        return chatParticipantRepository.findByChatIdAndUserId(chatId, userId)
                .map(p -> p.getRole() == ParticipantRole.ADMIN)
                .orElse(false);
    }

    public boolean isParticipant(String chatId, String userId) {
        return chatParticipantRepository.findByChatIdAndUserId(chatId, userId)
                .map(p -> p.getRole() == ParticipantRole.ADMIN || p.getRole() == ParticipantRole.MEMBER)
                .orElse(false);
    }
}