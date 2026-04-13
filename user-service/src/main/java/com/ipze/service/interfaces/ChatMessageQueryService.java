package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;

import java.util.List;

public interface ChatMessageQueryService {

    List<ChatMessageDto> getHistory(String senderId, String receiverId);

    List<ChatMessageDto> getRoomMessages(String roomId);

    List<ChatMessageDto> getIncomingMessages(String userId);
}