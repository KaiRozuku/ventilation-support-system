package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.service.interfaces.RabbitProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RabbitProducerImpl implements RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(ChatMessageDto dto) {
        rabbitTemplate.convertAndSend("chat.exchange", dto.getChatId(), dto);
    }
}