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
    public List<ChatMessage> getHistory(String senderId, String receiverId) {
        return chatMessageRepository.findChat(senderId, receiverId);
    }

    @Override
    public List<ChatMessage> getUserInbox(String receiverId) {
        return chatMessageRepository.findAllByReceiverIdOrderByTimestampAsc(receiverId);
    }

    @Override
    public List<ChatMessage> getByRoom(String roomId) {
        return chatMessageRepository.findAllByRoomIdOrderByTimestampAsc(roomId);
    }

}
