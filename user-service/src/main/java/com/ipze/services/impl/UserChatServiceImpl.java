package com.ipze.services.impl;

import com.ipze.client.interfaces.ChatRoomClient;
import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import com.ipze.services.interfaces.UserChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserChatServiceImpl implements UserChatService {

    private final ChatRoomClient chatRoomClient;

    @Override
    public Mono<List<ChatRoomDto>> getPrivateChats(String userId) {
        return chatRoomClient.getUserPrivateChats(userId);
    }

    @Override
    public Mono<ChatRoomDto> createPrivate(String userId, CreateRoomRequest createRoomRequest) {
        return chatRoomClient.getOrCreatePrivateChatRoom(userId, createRoomRequest);
    }

    @Override
    public Mono<List<ChatRoomDto>> getGroups(String userId) {
        return chatRoomClient.getUserGroups(userId);
    }

    @Override
    public Mono<ChatRoomDto> createGroup(String userId, CreateGroupRequest request) {
        return chatRoomClient.createGroup(userId, request);
    }

    @Override
    public Mono<Void> deleteChat(String userId, String chatId){
        return chatRoomClient.deleteChat(chatId, userId);
    }
}