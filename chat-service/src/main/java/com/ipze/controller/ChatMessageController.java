package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.security.LocalUserDetails;
import com.ipze.service.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Add CRUD in the future
 */
@RestController
@RequestMapping("chat/api")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/chats/{chatId}")
    public ResponseEntity<List<ChatMessageDto>> getMessagesFromChats(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user) {
        return ResponseEntity.ok(chatMessageService.getMessages(chatId, user.uuid()));
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<ChatMessageDto>> getMessagesFromGroups(
            @PathVariable String groupId,
            @AuthenticationPrincipal LocalUserDetails user) {
        return ResponseEntity.ok(chatMessageService.getMessages(groupId, user.uuid()));
    }
}