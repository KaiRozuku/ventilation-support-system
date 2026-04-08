package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.InviteStatus;
import com.ipze.mapper.ChatMessageMapper;
import com.ipze.service.interfaces.ChatCommandService;
import com.ipze.service.interfaces.ChatQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatQueryController {

    private final ChatQueryService chatQueryService;
    private final ChatCommandService chatCommandService;
    private final ChatMessageMapper chatMessageMapper;

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessageDto>> getHistory(@RequestParam String senderId,
                                                        @RequestParam String receiverId) {
        return ResponseEntity.ok(
                chatQueryService.getHistory(senderId, receiverId)
                        .stream()
                        .map(chatMessageMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/room/{roomUuid}")
    public ResponseEntity<List<ChatMessageDto>> getByRoom(@PathVariable String roomUuid) {
        return ResponseEntity.ok(
                chatQueryService.getByRoom(roomUuid)
                        .stream()
                        .map(chatMessageMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/inb")
    public ResponseEntity<List<ChatMessageDto>> getInbox(@RequestParam String receiverId){
        return ResponseEntity.ok(
                chatQueryService.getUserInbox(receiverId)
                        .stream()
                        .map(chatMessageMapper::toDto)
                        .toList()
        );
    }

    @PostMapping("/room/create")
    public ResponseEntity<String> createRoom(@RequestParam String user1,
                                             @RequestParam String user2) {
        if (!chatCommandService.sendInvite(user2).equals(InviteStatus.ACCEPTED)) //implement
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invite not accepted by user: " + user2);

        String roomUuid = UUID.nameUUIDFromBytes((user1 + user2).getBytes()).toString();
        return ResponseEntity.ok(roomUuid);
    }
}