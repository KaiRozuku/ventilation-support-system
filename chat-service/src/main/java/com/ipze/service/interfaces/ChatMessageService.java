package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;

import java.util.List;

public interface ChatMessageService {

    ChatMessageDto saveMessage(ChatMessageDto dto);

    List<ChatMessageDto> getMessages(String chatId, String userId);

    List<ChatMessageDto> getUnreadMessages(String chatId, String userId);
}