package com.ipze.controller;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreatePrivateRoomRequest;
import com.ipze.security.LocalUserDetails;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat/api")
@RequiredArgsConstructor
@PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.name)")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/private")
    public ResponseEntity<List<ChatRoomDto>> getUserPrivateChats(
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return ResponseEntity.ok(
                chatRoomService.getUserPrivateChats(user.uuid())
        );
    }

    @PostMapping("/private")
    public ResponseEntity<ChatRoomDto> getOrCreatePrivateChat(
            @AuthenticationPrincipal LocalUserDetails sender,
            @RequestBody CreatePrivateRoomRequest request
    ) {
        return ResponseEntity.ok(
                chatRoomService.getOrCreatePrivateChat(
                        sender.uuid(),
                        request.receiver()
                )
        );
    }

    @GetMapping("/groups")
    public ResponseEntity<List<ChatRoomDto>> getUserGroups(
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return ResponseEntity.ok(
                chatRoomService.getUserGroups(user.uuid())
        );
    }

    @PostMapping("/groups")
    public ResponseEntity<ChatRoomDto> createGroup(
            @AuthenticationPrincipal LocalUserDetails creator,
            @RequestBody CreateGroupRequest request
    ) {
        return ResponseEntity.ok(
                chatRoomService.createGroup(
                        request.users(),
                        request.groupName(),
                        creator.uuid()
                )
        );
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatRoomDto> getChat(
            @AuthenticationPrincipal LocalUserDetails user,
            @PathVariable String chatId
    ) {
        return ResponseEntity.ok(
                chatRoomService.getChatRoom(chatId, user.uuid())
        );
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        chatRoomService.deleteChat(chatId, user.uuid());
        return ResponseEntity.noContent().build();
    }
}