package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.request.*;
import com.ipze.security.LocalUserDetails;
import com.ipze.security.WsAuthentication;
import com.ipze.service.interfaces.ChatMessageWsService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWsController {

    private final ChatMessageWsService chatMessageWsService;


    @MessageMapping("/chat.send")
    public void send(SendMessageRequest request, Principal principal) {

        LocalUserDetails user = extract(principal);

        chatMessageWsService.processMessage(request, user.uuid());
    }

    @MessageMapping("/chat.update")
    public void update(UpdateMessageRequest request, Principal principal) {

        LocalUserDetails user = extract(principal);

        chatMessageWsService.update(request, user.uuid());
    }

    @MessageMapping("/chat.delete")
    public void delete(DeleteMessageRequest request, Principal principal) {

        LocalUserDetails user = extract(principal);

        chatMessageWsService.delete(request, user.uuid());
    }

    @MessageMapping("/chat.read")
    public void read(ChatMessageDto request, Principal principal) {

        LocalUserDetails user = extract(principal);

        chatMessageWsService.markAsRead(request, user.uuid());
    }

    @MessageMapping("/chat.leave")
    public void leave(ChatActionRequest request, Principal principal) {

        LocalUserDetails user = extract(principal);

        chatMessageWsService.leave(request, user.uuid());
    }

    private LocalUserDetails extract(Principal principal) {
        if (!(principal instanceof WsAuthentication auth)) {
            throw new AccessDeniedException("No WS auth");
        }
        return (LocalUserDetails) auth.getPrincipal();
    }
}