package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.request.*;
import com.ipze.entity.ChatParticipant;
import com.ipze.entity.ChatRoom;
import com.ipze.enums.ChatType;
import com.ipze.exceptions.ChatRoomNotFoundException;
import com.ipze.exceptions.ParticipantNotFoundException;
import com.ipze.repository.ChatParticipantRepository;
import com.ipze.repository.ChatRoomRepository;
import com.ipze.service.interfaces.ChatMessageService;
import com.ipze.service.interfaces.ChatMessageWsService;
import com.ipze.service.interfaces.ChatParticipantManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageWsServiceImpl implements ChatMessageWsService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageService chatMessageService;
    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatParticipantManagementService chatParticipantManagementService;

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#request.chatId(), #senderId)")
    public void processMessage(SendMessageRequest request, String senderId) {

        ChatRoom chatRoom = chatRoomRepository.findById(request.chatId())
                .orElseThrow(ChatRoomNotFoundException::new);

        ChatMessageDto saved = chatMessageService.savedMessage(request, senderId);

        if (chatRoom.getChatType() == ChatType.GROUP) {
            sendToGroup(saved);
        } else {
            sendToPrivate(saved, chatRoom, senderId);
        }
    }

    @Override
    @PreAuthorize("@messageSecurityService.isMessageOwner(#request.messageId(), #senderId)")
    public void update(UpdateMessageRequest request, String senderId) {

        ChatMessageDto updated = chatMessageService.updateMessage(
                request.messageId(),
                senderId,
                request.content()
        );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + updated.getChatId(),
                updated
        );
    }

    @Override
    @PreAuthorize("@chatSecurityService.isSender(#request, #senderId)")
    public void delete(DeleteMessageRequest request, String senderId) {

        ChatMessageDto deleted = chatMessageService.deleteMessage(
                request.messageId(),
                senderId
        );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + deleted.getChatId(),
                deleted
        );
    }

    @Override
    @PreAuthorize("@chatSecurityService.isSender(#request, #senderId)")
    public void markAsRead(ChatMessageDto request, String senderId) {

        ChatMessageDto updated = chatMessageService.markAsRead(request, senderId);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.getChatId() + "/read",
                updated
        );
    }

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#request.chatId(), #senderId)")
    public void leave(ChatActionRequest request, String senderId) {

        chatParticipantManagementService.leaveChat(request.chatId(), senderId);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.chatId(),
                new ChatEventDto("USER_LEFT", senderId)
        );
    }

    // ================= FIXED =================

    private void sendToGroup(ChatMessageDto message) {
        messagingTemplate.convertAndSend(
                "/topic/chat/" + message.getChatId(),
                message
        );
    }

    private void sendToPrivate(ChatMessageDto message, ChatRoom chat, String senderId) {

        List<ChatParticipant> participants =
                chatParticipantRepository.findByChatId(chat.getId());

        String receiverId = participants.stream()
                .map(ChatParticipant::getUserId)
                .filter(id -> !id.equals(senderId))
                .findFirst()
                .orElseThrow(ParticipantNotFoundException::new);

        messagingTemplate.convertAndSendToUser(
                receiverId,
                "/queue/messages",
                message
        );

        messagingTemplate.convertAndSendToUser(
                senderId,
                "/queue/messages",
                message
        );
    }
}