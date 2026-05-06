package com.ipze.controller;

import com.ipze.dto.ParticipantRole;
import com.ipze.dto.request.AddParticipantRequest;
import com.ipze.dto.request.ChatParticipantDto;
import com.ipze.services.interfaces.UserChatParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/chat/{chatId}/participants")
@RequiredArgsConstructor
public class UserChatParticipantController {

    private final UserChatParticipantService userChatParticipantService;

    @GetMapping
    public Mono<ResponseEntity<List<ChatParticipantDto>>> getParticipants(@PathVariable String chatId) {
        return userChatParticipantService.getParticipants(chatId)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    public Mono<ResponseEntity<ChatParticipantDto>> add(
            @PathVariable String chatId,
            @RequestBody AddParticipantRequest addParticipantRequest
            ) {
        return userChatParticipantService.addParticipant(chatId, addParticipantRequest)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<ChatParticipantDto>> removeParticipant(
            @PathVariable String chatId,
            @PathVariable String userId
    ) {
        return userChatParticipantService.removeParticipant(chatId, userId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{userId}/role")
    public Mono<ResponseEntity<ChatParticipantDto>> role(
            @PathVariable String chatId,
            @PathVariable String userId,
            @RequestParam ParticipantRole role
    ) {
        return userChatParticipantService.updateRole(chatId, userId, role)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{userId}/mute")
    public Mono<ResponseEntity<ChatParticipantDto>> mute(
            @PathVariable String chatId,
            @PathVariable String userId,
            @RequestParam boolean muted
    ) {
        return userChatParticipantService.muteParticipant(chatId, userId, muted)
                .map(ResponseEntity::ok);
    }
}