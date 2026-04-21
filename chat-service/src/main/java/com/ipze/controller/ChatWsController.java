package com.ipze.controller;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.request.*;
import com.ipze.service.interfaces.ChatMessageService;
import com.ipze.service.interfaces.ChatParticipantManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWsController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatParticipantManagementService chatParticipantManagementService;

    @MessageMapping("/chat.send")
    public void send(SendMessageRequest request, Principal principal) {

        ChatMessageDto saved = chatMessageService.sendMessage(
                request,
                principal.getName()
        );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + saved.getChatId(),
                saved
        );
    }

    @MessageMapping("/chat.update")
    public void update(UpdateMessageRequest request, Principal principal) {

        ChatMessageDto updated = chatMessageService.updateMessage(
                request.messageId(),
                principal.getName(),
                request.content()
        );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + updated.getChatId(),
                updated
        );
    }

    @MessageMapping("/chat.delete")
    public void delete(DeleteMessageRequest request, Principal principal) {

        ChatMessageDto deleted = chatMessageService.deleteMessage(
                request.messageId(),
                principal.getName()
        );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + deleted.getChatId(),
                deleted
        );
    }

    @MessageMapping("/chat.read")
    public void read(ChatMessageDto chatMessageDto, Principal principal) {

        ChatMessageDto updated = chatMessageService.markAsRead(
                chatMessageDto,
                principal.getName()
        );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + chatMessageDto.getChatId() + "/read",
                updated
        );
    }

    @MessageMapping("/chat.leave")
    public void leaveChat(ChatActionRequest request, Principal principal) {

        String userId = principal.getName();

        chatParticipantManagementService.leaveChat(request.chatId(), userId);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.chatId(),
                new ChatEventDto("USER_LEFT", userId)
        );
    }
}