package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.request.ChatActionRequest;
import com.ipze.dto.request.DeleteMessageRequest;
import com.ipze.dto.request.SendMessageRequest;
import com.ipze.dto.request.UpdateMessageRequest;

public interface ChatMessageWsService {

    void processMessage(SendMessageRequest request, String senderId);

    void update(UpdateMessageRequest request, String senderId);

    void delete(DeleteMessageRequest request, String senderId);

    void markAsRead(ChatMessageDto request, String senderId);

    void leave(ChatActionRequest request, String senderId);
}