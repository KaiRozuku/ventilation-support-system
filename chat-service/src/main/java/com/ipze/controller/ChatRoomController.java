package com.ipze.controller;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import com.ipze.security.LocalUserDetails;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * chat-service
 */
@RestController
@RequestMapping("/chat/api")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chats")
    public ResponseEntity<List<ChatRoomDto>> getUserChats(
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return ResponseEntity.ok(
                chatRoomService.getUserPrivateChats(user.uuid())
        );
    }

    @PostMapping("/chats")
    public ResponseEntity<ChatRoomDto> getOrCreateChatRoom(
            @AuthenticationPrincipal LocalUserDetails sender,
            @RequestBody CreateRoomRequest createRoomRequest
            ) {
        return ResponseEntity.ok(
                chatRoomService.getOrCreatePrivateChat(
                        sender.uuid(),
                        createRoomRequest.receiver()
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

    // CREATE group
    @PostMapping("/groups")
    public ResponseEntity<ChatRoomDto> createGroup(
            @RequestBody CreateGroupRequest request,
            @AuthenticationPrincipal LocalUserDetails creator
    ) {
        return ResponseEntity.ok(
                chatRoomService.createGroup(
                        request.users(),
                        request.name(),
                        creator.uuid()
                )
        );
    }
}