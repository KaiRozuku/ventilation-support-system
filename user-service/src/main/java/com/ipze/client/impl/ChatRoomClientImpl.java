package com.ipze.client.impl;

import com.ipze.client.interfaces.ChatRoomClient;
import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * change {@code .fromPath} to real URI's
 * user-services
 */
@Service
@RequiredArgsConstructor
public class ChatRoomClientImpl implements ChatRoomClient {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<List<ChatRoomDto>> getUserPrivateChats(String userId) {
        return webClientUtils.sendGetRequest(
                "/chat/private",
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<List<ChatRoomDto>> getUserGroups(String userId) {
        return webClientUtils.sendGetRequest(
                "/chat/groups",
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<Void> deleteChat(String chatId, String userId) {
        return webClientUtils.sendDeleteRequest(
                UriComponentsBuilder
                        .fromPath("/chat/{chatId}")
                        .buildAndExpand(chatId)
                        .toUriString(),
                Void.class
        );
    }

    @Override
    public Mono<ChatRoomDto> getOrCreatePrivateChatRoom(String userId, CreateRoomRequest request) {
        return webClientUtils.sendPostRequest(
                "/chat/private",
                request,
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<ChatRoomDto> createGroup(String userId, CreateGroupRequest request) {
        return webClientUtils.sendPostRequest(
                "/chat/groups",
                request,
                new ParameterizedTypeReference<>() {}
        );
    }
}