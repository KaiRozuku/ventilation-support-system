package com.ipze.service.interfaces;

import com.ipze.dto.ChatParticipantDto;
import com.ipze.enums.ParticipantRole;

public interface ChatParticipantManagementService {

    ChatParticipantDto addParticipant(String chatId, String userId, ParticipantRole role);

    void removeParticipant(String chatId, String userId);

    void leaveChat(String chatId, String userId);
}