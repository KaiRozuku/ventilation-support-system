package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.security.LocalUserDetails;
import com.ipze.service.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/api/{chatId}/messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService service;

    @GetMapping
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.principal.uuid)")
    public ResponseEntity<List<ChatMessageDto>> getMessages(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return ResponseEntity.ok(
                service.getMessagesOrderByTimestampAsc(chatId, user.uuid())
        );
    }

    @GetMapping("/unread")
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.principal.uuid)")
    public ResponseEntity<List<ChatMessageDto>> getUnread(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return ResponseEntity.ok(
                service.getUnreadMessages(chatId, user.uuid())
        );
    }

    @GetMapping("/search")
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.principal.uuid)")
    public ResponseEntity<List<ChatMessageDto>> search(
            @PathVariable String chatId,
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                service.searchMessages(chatId, keyword)
        );
    }

    @GetMapping("/last")
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.principal.uuid)")
    public ResponseEntity<ChatMessageDto> getLast(
            @PathVariable String chatId
    ) {
        return ResponseEntity.ok(
                service.getLastMessage(chatId)
        );
    }
}