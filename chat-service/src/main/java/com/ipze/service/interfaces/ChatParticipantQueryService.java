package com.ipze.service.interfaces;

import com.ipze.dto.ChatParticipantDto;

import java.util.List;

public interface ChatParticipantQueryService {

    List<ChatParticipantDto> getParticipants(String chatId);

    ChatParticipantDto getParticipant(String chatId, String userId);
}