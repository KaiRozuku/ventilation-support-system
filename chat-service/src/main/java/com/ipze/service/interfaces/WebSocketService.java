package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;

public interface WebSocketService {

    void sendToTopic(ChatMessageDto chatMessageDto);
}