package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;


public interface RabbitProducer {

    void send(ChatMessageDto chatMessageDto);
}