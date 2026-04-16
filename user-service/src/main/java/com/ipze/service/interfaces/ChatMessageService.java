package com.ipze.service.interfaces;


import com.ipze.dto.response.ChatMessageDto;

public interface ChatMessageService {

    void sendToUser(ChatMessageDto chatMessageDto);
}