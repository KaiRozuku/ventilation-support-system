package com.ipze.services.impl;

import com.ipze.client.interfaces.ChatMessageClient;
import com.ipze.dto.ChatMessageDto;
import com.ipze.services.interfaces.UserChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChatMessageServiceImpl implements UserChatMessageService {

    private final ChatMessageClient chatMessageClient;

    @Override
    public Mono<List<ChatMessageDto>> getMessages(String chatId, String userId) {
        return chatMessageClient.getMessages(chatId);
    }

    @Override
    public Mono<List<ChatMessageDto>> getUnread(String chatId, String userId) {
        return chatMessageClient.getUnread(chatId, userId);
    }

    @Override
    public Mono<ChatMessageDto> getLast(String chatId) {
        return chatMessageClient.getLast(chatId);
    }

    @Override
    public Mono<List<ChatMessageDto>> getSearchedMessages(String chatId, String userId, String keyword) {
        return chatMessageClient.getSearchedMessages(chatId, keyword);
    }
}