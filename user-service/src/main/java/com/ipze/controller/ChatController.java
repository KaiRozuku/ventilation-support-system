package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.MessageType;
import com.ipze.service.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/user/chat")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ChatController {

    private final ChatMessageService chatMessageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestParam String content,
                            @RequestParam String senderId,
                            @RequestParam String receiverId) {

        ChatMessageDto dto = ChatMessageDto.builder()
                .id(UUID.randomUUID().toString())
                .content(content)
                .senderId(senderId)
                .receiverId(receiverId)
                .roomId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .messageType(MessageType.CHAT)
                .build();

        chatMessageService.sendToUser(dto);

        return ResponseEntity.ok(dto);
    }
}