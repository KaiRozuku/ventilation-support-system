package com.ipze.controller;

import com.ipze.dto.ChatParticipantDto;
import com.ipze.enums.ParticipantRole;
import com.ipze.service.interfaces.ChatParticipantManagementService;
import com.ipze.service.interfaces.ChatParticipantQueryService;
import com.ipze.service.interfaces.ChatParticipantStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/{chatId}/participants")
@RequiredArgsConstructor
public class ChatParticipantController {

    private final ChatParticipantQueryService chatParticipantQueryService;
    private final ChatParticipantStateService chatParticipantStateService;
    private final ChatParticipantManagementService chatParticipantManagementService;

    @PostMapping
    public ResponseEntity<ChatParticipantDto> addParticipant(
            @PathVariable String chatId,
            @RequestParam String userId,
            @RequestParam ParticipantRole role
    ) {
        return ResponseEntity.ok(
                chatParticipantManagementService.addParticipant(chatId, userId, role)
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeParticipant(
            @PathVariable String chatId,
            @PathVariable String userId
    ) {
        chatParticipantManagementService.removeParticipant(chatId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ChatParticipantDto>> getParticipants(
            @PathVariable String chatId
    ) {
        return ResponseEntity.ok(
                chatParticipantQueryService.getParticipants(chatId)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ChatParticipantDto> getParticipant(
            @PathVariable String chatId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(
                chatParticipantQueryService.getParticipant(chatId, userId)
        );
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<ChatParticipantDto> updateRole(
            @PathVariable String chatId,
            @PathVariable String userId,
            @RequestParam ParticipantRole role
    ) {
        return ResponseEntity.ok(
                chatParticipantStateService.updateRole(chatId, userId, role)
        );
    }

    @PutMapping("/{userId}/mute")
    public ResponseEntity<ChatParticipantDto> muteParticipant(
            @PathVariable String chatId,
            @PathVariable String userId,
            @RequestParam boolean muted
    ) {
        return ResponseEntity.ok(
                chatParticipantStateService.mute(chatId, userId, muted)
        );
    }
}