package com.ipze.controller;


import com.ipze.service.interfaces.ChatMessageQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/chat")
@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")

public class ChatQueryController {

    private final ChatMessageQueryService chatMessageQueryService;

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(String senderId, String receiverId, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(chatMessageQueryService.getHistory(senderId, receiverId, httpServletRequest));
    }
}