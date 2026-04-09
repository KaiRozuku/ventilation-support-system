package com.ipze.listener;

import com.ipze.dto.ChatMessageDto;
import com.ipze.service.interfaces.ChatCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatMessageListener {

    private final ChatCommandService chatCommandService;

    @RabbitListener(queues = "chat.queue")
    public void receiveMessage(ChatMessageDto message) {

        chatCommandService.processIncomingMessage(message);

        log.info("Message from {} to {} delivered", message.getSenderId(), message.getReceiverId());
    }
}