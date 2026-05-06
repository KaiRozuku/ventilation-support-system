package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.security.LocalUserDetails;
import com.ipze.service.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{chatId}/messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping
    public ResponseEntity<List<ChatMessageDto>> getMessages(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return ResponseEntity.ok(
                chatMessageService.getMessagesOrderByTimestampAsc(chatId, user.uuid())
        );
    }

    @GetMapping("/unread")
    public ResponseEntity<List<ChatMessageDto>> getUnread(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return ResponseEntity.ok(
                chatMessageService.getUnreadMessages(chatId, user.uuid())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<ChatMessageDto>> search(
            @PathVariable String chatId,
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                chatMessageService.searchMessages(chatId, keyword)
        );
    }

    @GetMapping("/last")
    public ResponseEntity<ChatMessageDto> getLast(
            @PathVariable String chatId
    ) {
        return ResponseEntity.ok(
                chatMessageService.getLastMessage(chatId)
        );
    }
}