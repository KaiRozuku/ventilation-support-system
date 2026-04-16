package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.entity.ChatMessage;
import com.ipze.entity.MessageStatus;
import com.ipze.mapper.ChatMessageMapper;
import com.ipze.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatMessageMapper chatMessageMapper;

    public ChatMessage save(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now());
        message.setStatus(MessageStatus.DELIVERED);
        return repository.save(message);
    }

    public List<ChatMessageDto> getChatMessages(String chatId) {
        return repository.findByChatIdOrderByTimestampAsc(chatId)
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }

    public List<ChatMessageDto> getUnreadMessages(String chatId, String userId) {
        return repository.findByChatIdAndSenderIdNotAndStatus(
                chatId,
                userId,
                MessageStatus.RECEIVED
        )
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }
}