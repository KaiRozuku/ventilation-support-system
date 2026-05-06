package com.ipze.client.interfaces;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * change signature of getUserGroups method
 * user-service
 */
public interface ChatRoomClient {

    Mono<List<ChatRoomDto>> getUserPrivateChats(String userId);

    Mono<ChatRoomDto> getOrCreatePrivateChatRoom(String userId, CreateRoomRequest createRoomRequest);

    Mono<ChatRoomDto> createGroup(String userId, CreateGroupRequest createGroupRequest);

    Mono<List<ChatRoomDto>> getUserGroups(String userId);

    Mono<Void> deleteChat(String chatId, String userID);
}