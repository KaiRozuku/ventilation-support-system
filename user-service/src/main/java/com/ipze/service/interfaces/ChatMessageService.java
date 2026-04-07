package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;

public interface ChatMessageService {
    void sendToUser(ChatMessageDto chatMessageDto);
}