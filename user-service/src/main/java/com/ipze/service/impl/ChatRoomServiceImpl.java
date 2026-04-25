package com.ipze.service.impl;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import com.ipze.service.interfaces.ChatRoomService;
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
public class ChatRoomServiceImpl implements ChatRoomService {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<List<ChatRoomDto>> getUserPrivateChats() {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/chat-services/api/private")
                        .toUriString()
                );
    }
    @Override
    public Mono<List<ChatRoomDto>> getUserGroups() {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/chat-services/api/groups")
                        .toUriString()
                );
    }

    @Override
    public Mono<ChatRoomDto> getOrCreatePrivateChatRoom(CreateRoomRequest createRoomRequest) {
        return webClientUtils.sendPostRequest(
                        UriComponentsBuilder
                                .fromPath("/chat-services/api/private")
                                .toUriString(),
                        createRoomRequest,
                        new ParameterizedTypeReference<>() {
                        }
                );
    }

    @Override
    public Mono<ChatRoomDto> createGroup(CreateGroupRequest createGroupRequest) {
        return webClientUtils.sendPostRequest(
                        UriComponentsBuilder
                                .fromPath("/chat-services/api/groups")
                                .toUriString(),
                        createGroupRequest,
                        new ParameterizedTypeReference<>() {
                        }
                );
    }
}