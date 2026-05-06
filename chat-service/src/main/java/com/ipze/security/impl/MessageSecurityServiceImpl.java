package com.ipze.security.impl;

import com.ipze.repository.ChatMessageRepository;
import com.ipze.security.interfaces.MessageSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component("messageSecurityService")
@RequiredArgsConstructor
public class MessageSecurityServiceImpl implements MessageSecurityService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public boolean isMessageOwner(String messageId, String userId) {
        return chatMessageRepository.findById(messageId)
                .map(message -> message.getSenderId().equals(userId))
                .orElse(false);
    }

    public boolean canEditMessage(String messageId, String userId) {
        return chatMessageRepository.findById(messageId)
                .map(message -> message.getSenderId().equals(userId))
                .orElse(false);
    }
}