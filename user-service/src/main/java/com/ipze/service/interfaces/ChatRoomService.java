package com.ipze.service.interfaces;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * change signature of getUserGroups method
 * user-service
 */
public interface ChatRoomService {

    Mono<List<ChatRoomDto>> getUserPrivateChats();

    Mono<ChatRoomDto> getOrCreatePrivateChatRoom(CreateRoomRequest createRoomRequest);

    Mono<ChatRoomDto> createGroup(CreateGroupRequest createGroupRequest);

    Mono<List<ChatRoomDto>> getUserGroups();
}