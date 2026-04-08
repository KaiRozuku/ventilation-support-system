package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.service.interfaces.RabbitProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RabbitProducer rabbitProducer;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDto dto) {
        if(dto.getRoomId() == null || dto.getRoomId().isEmpty()){
            dto.setRoomId(UUID.nameUUIDFromBytes((dto.getSenderId() + dto.getReceiverId()).getBytes()).toString());
        }
        rabbitProducer.send(dto);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDto addUser(@Payload ChatMessageDto chatMessageDto,
                                  SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes())
                .put("username", chatMessageDto.getSenderId());
        return chatMessageDto;
    }
}