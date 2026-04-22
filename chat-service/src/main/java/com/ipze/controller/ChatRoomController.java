package com.ipze.controller;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreatePrivateRoomRequest;
import com.ipze.security.LocalUserDetails;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chat/api")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/private")
    public ResponseEntity<List<ChatRoomDto>> getUserPrivateChats(
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        var l = chatRoomService.getUserPrivateChats(user.uuid());
        log.info("chatroom {}", l );
        return ResponseEntity.ok(l
        );
    }

    @PostMapping("/private")
    public ResponseEntity<ChatRoomDto> getOrCreatePrivateChat(
            @AuthenticationPrincipal LocalUserDetails sender,
            @RequestBody CreatePrivateRoomRequest request
    ) {
        log.info("request -> {}", request.receiver());
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
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.principal.uuid)")
    public ResponseEntity<ChatRoomDto> getChat(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return ResponseEntity.ok(
                chatRoomService.getChatRoom(chatId, user.uuid())
        );
    }

    @DeleteMapping("/{chatId}")
    @PreAuthorize("@chatSecurityService.isChatAdmin(#chatId, authentication.principal.uuid)")
    public ResponseEntity<Void> deleteChat(
            @PathVariable String chatId,
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        chatRoomService.deleteChat(chatId, user.uuid());
        return ResponseEntity.noContent().build();
    }
}