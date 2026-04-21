package com.ipze.controller;

import com.ipze.dto.ChatParticipantDto;
import com.ipze.enums.ParticipantRole;
import com.ipze.service.interfaces.ChatParticipantManagementService;
import com.ipze.service.interfaces.ChatParticipantQueryService;
import com.ipze.service.interfaces.ChatParticipantStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("chat/api/{chatId}/participants")
@RequiredArgsConstructor
@EnableMethodSecurity
public class ChatParticipantController {

    private final ChatParticipantQueryService chatParticipantQueryService;
    private final ChatParticipantStateService chatParticipantStateService;
    private final ChatParticipantManagementService chatParticipantManagementService;

    @PreAuthorize("@chatSecurityService.isChatAdmin(#chatId, authentication.name)")
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

    @PreAuthorize("@chatSecurityService.isChatAdmin(#chatId, authentication.name)")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeParticipant(
            @PathVariable String chatId,
            @PathVariable String userId
    ) {
        chatParticipantManagementService.removeParticipant(chatId, userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.name)")    @GetMapping
    public ResponseEntity<List<ChatParticipantDto>> getParticipants(
            @PathVariable String chatId
    ) {
        return ResponseEntity.ok(
                chatParticipantQueryService.getParticipants(chatId)
        );
    }

    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.name)")    @GetMapping("/{userId}")
    public ResponseEntity<ChatParticipantDto> getParticipant(
            @PathVariable String chatId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(
                chatParticipantQueryService.getParticipant(chatId, userId)
        );
    }

    @PreAuthorize("@chatSecurityService.isChatAdmin(#chatId, authentication.name)")
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

    @PreAuthorize("@chatSecurityService.isChatAdmin(#chatId, authentication.name)")
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

    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.name)")    @PutMapping("/{userId}/read")
    public ResponseEntity<ChatParticipantDto> updateLastRead(
            @PathVariable String chatId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(
                chatParticipantStateService.updateLastRead(
                        chatId,
                        userId,
                        LocalDateTime.now()
                )
        );
    }
}