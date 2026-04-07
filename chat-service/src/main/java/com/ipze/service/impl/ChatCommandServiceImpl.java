package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.InviteStatus;
import com.ipze.entity.ChatMessage;
import com.ipze.repository.ChatMessageRepository;
import com.ipze.service.interfaces.ChatCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatCommandServiceImpl implements ChatCommandService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void sendToUser(ChatMessageDto chatMessageDto) {

        chatMessageRepository.save(ChatMessage.builder()
                .senderId(chatMessageDto.getSenderId())
                .receiverId(chatMessageDto.getReceiverId())
                .content(chatMessageDto.getContent())
                .roomId(chatMessageDto.getRoomId())
                .timestamp(chatMessageDto.getTimestamp())
                .messageType(chatMessageDto.getMessageType())
                .build());
    }

    @Override
    public InviteStatus sendInvite(String receiverId) {
        return InviteStatus.WAITING;
    }
}