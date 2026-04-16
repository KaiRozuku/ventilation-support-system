package com.ipze.service.impl;

import com.ipze.dto.response.ChatMessageDto;
import com.ipze.service.interfaces.ChatMessageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * change {@code .fromPath} to real URI's
 */
@Service
@RequiredArgsConstructor
public class ChatMessageQueryServiceImpl implements ChatMessageQueryService {

    private final WebClientUtils webClientUtils;

    @Override
    public List<ChatMessageDto> getHistory(String senderId, String receiverId) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<List<ChatMessageDto>>() {},
                UriComponentsBuilder
                        .fromPath("/chat-service/api/messages/{receiverId}")
                        .buildAndExpand(receiverId)
                        .toUriString()
        ).block();
    }

    @Override
    public List<ChatMessageDto> getRoomMessages(String roomId) {
        return webClientUtils.sendGetRequest(
                        new ParameterizedTypeReference<List<ChatMessageDto>>() {},
                        UriComponentsBuilder
                                .fromPath("/rooms/{roomId}/messages")
                                .buildAndExpand(roomId)
                                .toUriString()
                )
                .block();
    }

    @Override
    public List<ChatMessageDto> getIncomingMessages(String userId) {
        return webClientUtils.sendGetRequest(
                        new ParameterizedTypeReference<List<ChatMessageDto>>() {},
                        UriComponentsBuilder
                                .fromPath("/users/{userId}/messages/incoming")
                                .buildAndExpand(userId)
                                .toUriString())
                .block();
    }
}