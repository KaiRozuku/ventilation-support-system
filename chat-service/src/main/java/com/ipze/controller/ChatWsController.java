package com.ipze.controller;

import com.ipze.entity.ChatMessage;
import com.ipze.service.impl.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ChatWsController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message) {

        ChatMessage saved = chatMessageService.save(message);

        messagingTemplate.convertAndSend(
                "/topic/chats/" + message.getChatId(),
                saved
        );
    }
}