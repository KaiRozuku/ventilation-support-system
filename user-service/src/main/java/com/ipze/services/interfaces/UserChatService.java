package com.ipze.services.interfaces;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserChatService {

    Mono<List<ChatRoomDto>> getPrivateChats(String userId);

    Mono<ChatRoomDto> createPrivate(String userId, CreateRoomRequest createRoomRequest);

    Mono<List<ChatRoomDto>> getGroups(String userId);

    Mono<ChatRoomDto> createGroup(String userId, CreateGroupRequest request);

    Mono<Void> deleteChat(String userId, String chatId);
}