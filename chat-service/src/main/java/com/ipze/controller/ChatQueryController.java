package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.mapper.ChatMessageMapper;
import com.ipze.service.interfaces.ChatQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// * Спочатку надати доступ лише p2p спілкуванню
@RestController
@RequestMapping("/chat/api")
@RequiredArgsConstructor
public class ChatQueryController {

    private final ChatQueryService chatQueryService;
    private final ChatMessageMapper chatMessageMapper;


    @GetMapping("/users/{senderId}/messages/{receiverId}")
    public ResponseEntity<List<ChatMessageDto>> messagesFromChat(
            @PathVariable String senderId,
            @PathVariable String receiverId
    ) {
        return ResponseEntity.ok(
                chatQueryService.getMessagesFromChat(senderId, receiverId)
                        .stream()
                        .map(chatMessageMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessageDto>> messagesFromGroup(@PathVariable String roomId) {
        return ResponseEntity.ok(
                chatQueryService.getMessagesFromGroup(roomId)
                        .stream()
                        .map(chatMessageMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/users/{userId}/messages/incoming")
    public ResponseEntity<List<ChatMessageDto>> getIncomingMessages(@PathVariable String userId) {
        return ResponseEntity.ok(
                chatQueryService.getUserIncomingMessages(userId)
                        .stream()
                        .map(chatMessageMapper::toDto)
                        .toList()
        );
    }
}