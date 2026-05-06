package com.ipze.controller;

import com.ipze.config.LocalUserDetails;
import com.ipze.dto.ChatMessageDto;
import com.ipze.services.interfaces.UserChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/chat/{chatId}/messages")
@RequiredArgsConstructor
public class UserChatMessageController {

    private final UserChatMessageService userChatMessageService;

    @GetMapping
    public Mono<ResponseEntity<List<ChatMessageDto>>> messages(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return userChatMessageService.getMessages(chatId, user.uuid())
                .map(ResponseEntity::ok);
    }

    @GetMapping("/unread")
    public Mono<ResponseEntity<List<ChatMessageDto>>> unread(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return userChatMessageService.getUnread(chatId, user.uuid())
                .map(ResponseEntity::ok);
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<List<ChatMessageDto>>> getSearchedMessages(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user,
            @RequestParam String keyword
    ) {
        return userChatMessageService.getSearchedMessages(chatId, user.uuid(), keyword)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/last")
    public Mono<ResponseEntity<ChatMessageDto>> last(
            @PathVariable String chatId
    ) {
        return userChatMessageService.getLast(chatId)
                .map(ResponseEntity::ok);
    }
}