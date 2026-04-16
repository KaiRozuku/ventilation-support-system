package com.ipze.controller;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * user-service
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chats")
    public ResponseEntity<List<ChatRoomDto>> getUserChats(){
        return ResponseEntity.ok(chatRoomService.getUserChats());
    }

    @PostMapping("/chats")
    public ResponseEntity<ChatRoomDto> getOrCreateChatRoom(@RequestBody CreateRoomRequest createRoomRequest){
        return ResponseEntity.ok(chatRoomService.getOrCreateChatRoom(createRoomRequest));
    }

    @GetMapping("/groups")
    public ResponseEntity<List<ChatRoomDto>> getUserGroups(){
        return ResponseEntity.ok(chatRoomService.getUserGroups());
    }

    @PostMapping("/groups")
    public ResponseEntity<ChatRoomDto> createGroup(@RequestBody CreateGroupRequest createGroupRequest){
        return ResponseEntity.ok(chatRoomService.createGroup(createGroupRequest));
    }
}