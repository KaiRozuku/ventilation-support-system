package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.service.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWsController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessageDto message) {

        ChatMessageDto saved = chatMessageService.saveMessage(message);

        simpMessagingTemplate.convertAndSend(
                "/topic/chat/" + saved.getChatId(),
                saved
        );
    }
}