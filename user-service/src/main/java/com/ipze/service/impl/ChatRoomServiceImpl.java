package com.ipze.service.impl;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * change {@code .fromPath} to real URI's
 * user-service
 */
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final WebClientUtils webClientUtils;

    @Override
    public List<ChatRoomDto> getUserChats() {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<List<ChatRoomDto>>() {},
                UriComponentsBuilder
                        .fromPath("/chat-service/api/chats")
                        .toUriString()
                ).block();
    }
    @Override
    public List<ChatRoomDto> getUserGroups() {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<List<ChatRoomDto>>() {},
                UriComponentsBuilder
                        .fromPath("/chat-service/api/groups")
                        .toUriString()
                ).block();
    }

    @Override
    public ChatRoomDto getOrCreateChatRoom(CreateRoomRequest createRoomRequest) {
        return webClientUtils.sendPostRequest(
                        UriComponentsBuilder
                                .fromPath("/chat-service/api/chats")
                                .toUriString(),
                        createRoomRequest,
                        new ParameterizedTypeReference<ChatRoomDto>() {
                        }
                )
                .block();
    }

    @Override
    public ChatRoomDto createGroup(CreateGroupRequest createGroupRequest) {
        return webClientUtils.sendPostRequest(
                        UriComponentsBuilder
                                .fromPath("/chat-service/api/groups")
                                .toUriString(),
                        createGroupRequest,
                        new ParameterizedTypeReference<ChatRoomDto>() {
                        }
                )
                .block();
    }
}