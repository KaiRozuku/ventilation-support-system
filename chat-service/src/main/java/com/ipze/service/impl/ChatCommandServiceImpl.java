package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.InviteStatus;
import com.ipze.mapper.ChatMessageMapper;
import com.ipze.repository.ChatMessageRepository;
import com.ipze.service.interfaces.ChatCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatCommandServiceImpl implements ChatCommandService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void processIncomingMessage(ChatMessageDto chatMessageDto) {
        chatMessageRepository.save(chatMessageMapper.toEntity(chatMessageDto));

        messagingTemplate.convertAndSend(
//                "/user/" + chatMessageDto.getReceiverId() + "/queue/messages",
                null,
                chatMessageDto
        );
    }

    @Override
    public InviteStatus sendInvite(String receiverId) {
        return InviteStatus.WAITING;
    }
}