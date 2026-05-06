package com.ipze.client.impl;

import com.ipze.client.interfaces.ChatMessageClient;
import com.ipze.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageClientImpl implements ChatMessageClient {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<List<ChatMessageDto>> getMessages(String chatId) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("chat/{chatId}/messages")
                        .buildAndExpand(chatId)
                        .toUriString(),
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<List<ChatMessageDto>> getUnread(String chatId, String userId) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("chat/{chatId}/messages/unread")
                        .buildAndExpand(chatId)
                        .toUriString(),
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<ChatMessageDto> getLast(String chatId) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("chat/{chatId}/messages/last")
                        .buildAndExpand(chatId)
                        .toUriString(),
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<List<ChatMessageDto>> getSearchedMessages(String chatId, String keyword) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("chat/{chatId}/messages/search")
                        .queryParam("keyword", keyword)
                        .buildAndExpand(chatId)
                        .toUriString(),
                new ParameterizedTypeReference<>() {}
        );
    }
}