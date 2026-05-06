package com.ipze.controller;

import com.ipze.config.LocalUserDetails;
import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import com.ipze.services.interfaces.UserChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class UserChatController {

    private final UserChatService userChatService;

    @GetMapping("/private")
    public Mono<ResponseEntity<List<ChatRoomDto>>> privateChats(
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return userChatService.getPrivateChats(user.uuid())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/private")
    public Mono<ResponseEntity<ChatRoomDto>> createPrivate(
            @AuthenticationPrincipal LocalUserDetails user,
            @RequestBody CreateRoomRequest req
    ) {
        return userChatService.createPrivate(user.uuid(), req)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/groups")
    public Mono<ResponseEntity<List<ChatRoomDto>>> groups(
            @AuthenticationPrincipal LocalUserDetails user
    ) {
        return userChatService.getGroups(user.uuid())
                .map(ResponseEntity::ok);
    }

    @PostMapping("/groups")
    public Mono<ResponseEntity<ChatRoomDto>> createGroup(
            @AuthenticationPrincipal LocalUserDetails user,
            @RequestBody CreateGroupRequest req
    ) {
        return userChatService.createGroup(user.uuid(), req)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{chatId}")
    public Mono<Void> deleteChat(@AuthenticationPrincipal LocalUserDetails user,
                                 @PathVariable String chatId){
        return userChatService.deleteChat(user.uuid(), chatId);
    }
}