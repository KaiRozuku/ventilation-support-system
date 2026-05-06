package com.ipze.service.interfaces;

import com.ipze.dto.ChatParticipantDto;
import com.ipze.enums.ParticipantRole;

import java.time.LocalDateTime;

public interface ChatParticipantStateService {

    ChatParticipantDto updateRole(String chatId, String userId, ParticipantRole role);

    ChatParticipantDto mute(String chatId, String userId, boolean muted);
}