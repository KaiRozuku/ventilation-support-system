package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.request.SendMessageRequest;

import java.util.List;

public interface ChatMessageService {

    ChatMessageDto savedMessage(SendMessageRequest dto, String userId);

    List<ChatMessageDto> getMessagesOrderByTimestampAsc(String chatId, String userId);

    List<ChatMessageDto> getUnreadMessages(String chatId, String userId);

    ChatMessageDto updateMessage(String messageId, String userId, String content);

    ChatMessageDto deleteMessage(String messageId, String userId);

    ChatMessageDto getLastMessage(String chatId);

    List<ChatMessageDto> searchMessages(String chatId, String keyword);

    ChatMessageDto markAsRead(ChatMessageDto message, String userId);
}