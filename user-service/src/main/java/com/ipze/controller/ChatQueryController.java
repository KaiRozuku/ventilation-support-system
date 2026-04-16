package com.ipze.controller;


import com.ipze.dto.request.ChatHistoryRequest;
import com.ipze.dto.response.ChatMessageDto;
import com.ipze.service.interfaces.ChatMessageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user/chat")

public class ChatQueryController {

    private final ChatMessageQueryService chatMessageQueryService;

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessageDto>> getHistory(@RequestBody ChatHistoryRequest request,
                                                           @RequestHeader("X-User-ID") String senderId
    ) {
        return ResponseEntity.ok(
                chatMessageQueryService.getHistory(
                        senderId,
                        request.receiverId()
                )
        );
    }
}