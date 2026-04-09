package com.ipze.controller;

import com.ipze.dto.InviteStatus;
import com.ipze.dto.request.CreateRoomRequest;
import com.ipze.service.interfaces.ChatCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/chat/api")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ChatCommandController {

    private final ChatCommandService chatCommandService;

    @PostMapping("/rooms")
    public ResponseEntity<String> createRoom(
            @RequestBody CreateRoomRequest request
    ) {
        if (!chatCommandService.sendInvite(request.receiver())
                .equals(InviteStatus.ACCEPTED)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Invite not accepted by user: " + request.receiver());
        }

        String roomId = UUID.nameUUIDFromBytes(
                (request.sender() + request.receiver()).getBytes()
        ).toString();

        return ResponseEntity.ok(roomId);
    }
}