package com.ipze.service.impl;

import com.ipze.dto.response.ChatMessageDto;
import com.ipze.service.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendToUser(ChatMessageDto chatMessageDto) {
        rabbitTemplate.convertAndSend(
                "chat.user.exchange",
//                chatMessageDto.getReceiverId(),
                chatMessageDto
        );
    }
}