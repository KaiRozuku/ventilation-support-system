package com.ipze.controller;


import com.ipze.dto.request.ChatHistoryRequest;
import com.ipze.service.interfaces.ChatMessageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user/chat")
@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")

public class ChatQueryController {

    private final ChatMessageQueryService chatMessageQueryService;

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(@RequestBody ChatHistoryRequest request) {

        return ResponseEntity.ok(
                chatMessageQueryService.getHistory(
                        request.senderId(),
                        request.receiverId()
                )
        );
    }
}