package com.ipze.service.impl;

import com.ipze.entity.ChatMessage;
import com.ipze.repository.ChatMessageRepository;
import com.ipze.service.interfaces.ChatQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatQueryServiceImpl implements ChatQueryService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public List<ChatMessage> getMessagesFromChat(String senderId, String receiverId) {
        return chatMessageRepository.findChat(senderId, receiverId);
    }

    @Override
    public List<ChatMessage> getUserIncomingMessages(String receiverId) {
        return chatMessageRepository.findAllByReceiverIdOrderByTimestampAsc(receiverId);
    }

    @Override
    public List<ChatMessage> getMessagesFromGroup(String roomId) {
        return chatMessageRepository.findAllByRoomIdOrderByTimestampAsc(roomId);
    }
}