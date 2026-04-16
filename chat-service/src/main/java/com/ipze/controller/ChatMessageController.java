package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.service.impl.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("chat/api")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    //"/chats/{chatId}"
    @GetMapping("/messages/{chatId}")
    public ResponseEntity<List<ChatMessageDto>> getMessages(@PathVariable String chatId) {
        return ResponseEntity.ok(chatMessageService.getChatMessages(chatId));
    }
}