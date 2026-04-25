package com.ipze.controller;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * user-services
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/private")
    public Mono<ResponseEntity<List<ChatRoomDto>>> getUserChats(){
        return (chatRoomService.getUserPrivateChats())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/private")
    public Mono<ResponseEntity<ChatRoomDto>>
    getOrCreateChatRoom(@RequestBody CreateRoomRequest createRoomRequest){
        return (chatRoomService.getOrCreatePrivateChatRoom(createRoomRequest))
                .map(ResponseEntity::ok);
    }

    @GetMapping("/groups")
    public Mono<ResponseEntity<List<ChatRoomDto>>> getUserGroups(){
        return (chatRoomService.getUserGroups())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/groups")
    public Mono<ResponseEntity<ChatRoomDto>>
    createGroup(@RequestBody CreateGroupRequest createGroupRequest){
        return (chatRoomService.createGroup(createGroupRequest))
                .map(ResponseEntity::ok);
    }
}