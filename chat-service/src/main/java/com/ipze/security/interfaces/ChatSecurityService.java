package com.ipze.security.interfaces;

import com.ipze.entity.ChatMessage;


public interface ChatSecurityService {

    boolean isChatAdmin(String chatId, String userId);

    boolean isParticipant(String chatId, String userId);

    boolean isParticipantInChats(String userId);

    boolean isSender(ChatMessage message, String userId);
}