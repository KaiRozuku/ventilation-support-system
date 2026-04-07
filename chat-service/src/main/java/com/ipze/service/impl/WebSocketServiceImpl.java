package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.service.interfaces.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void sendToTopic(ChatMessageDto dto) {
        messagingTemplate.convertAndSend("/topic/public", dto);
    }
}