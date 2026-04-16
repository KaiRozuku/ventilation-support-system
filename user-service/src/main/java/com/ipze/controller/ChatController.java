package com.ipze.controller;

import com.ipze.dto.response.ChatMessageDto;
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
                            @RequestParam String chatId) {

        ChatMessageDto dto = ChatMessageDto.builder()
                .id(UUID.randomUUID().toString())
                .content(content)
                .senderId(senderId)
                .chatId(chatId)
                .timestamp(LocalDateTime.now())
                .build();

        chatMessageService.sendToUser(dto);

        return ResponseEntity.ok(dto);
    }
}