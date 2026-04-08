package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.service.interfaces.ChatMessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final RabbitTemplate rabbitTemplate;
    private final WebClientUtils webClientUtils;

    @Override
    public void sendToUser(ChatMessageDto chatMessageDto) {
        rabbitTemplate.convertAndSend(
                "chat.user.exchange",
                chatMessageDto.getReceiverId(),
                chatMessageDto
        );
    }

    @Override
    public List<ChatMessageDto> getHistory(HttpServletRequest httpServletRequest) {
       return webClientUtils.sendGetRequest(
               new ParameterizedTypeReference<List<ChatMessageDto>>(){},
               "/",
               httpServletRequest
       ).block();
    }
}