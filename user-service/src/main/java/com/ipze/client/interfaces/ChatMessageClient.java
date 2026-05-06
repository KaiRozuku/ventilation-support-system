package com.ipze.client.interfaces;

import com.ipze.dto.ChatMessageDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChatMessageClient {

    Mono<List<ChatMessageDto>> getMessages(String chatId);

    Mono<List<ChatMessageDto>> getUnread(String chatId, String userId);

    Mono<ChatMessageDto> getLast(String chatId);

    Mono<List<ChatMessageDto>> getSearchedMessages(String chatId, String keyword);
}