package com.ipze.security.impl;

import com.ipze.entity.ChatMessage;
import com.ipze.enums.ParticipantStatus;
import com.ipze.repository.ChatParticipantRepository;
import com.ipze.security.interfaces.ChatSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("chatSecurityService")
@RequiredArgsConstructor
public class ChatSecurityServiceImpl implements ChatSecurityService {

    private final ChatParticipantRepository chatParticipantRepository;

    @Override
    public boolean isChatAdmin(String chatId, String userId) {
        return chatParticipantRepository.findByChatIdAndUserId(chatId, userId)
                .map(p -> p.getRole().name().equals("ADMIN"))
                .orElse(false);
    }

    @Override
    public boolean isParticipant(String chatId, String userId) {
        return chatParticipantRepository.findByChatIdAndUserId(chatId, userId)
                .map(p -> p.getStatus() == ParticipantStatus.ACTIVE)
                .orElse(false);
    }

    @Override
    public boolean isParticipantInChats(String userId) {
        return chatParticipantRepository.existsByChatIdAndUserId(userId, userId);
    }

    @Override
    public boolean isSender(ChatMessage message, String userId) {
        return message != null
                && message.getSenderId() != null
                && message.getSenderId().equals(userId);
    }
}