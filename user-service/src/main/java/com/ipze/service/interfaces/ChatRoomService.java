package com.ipze.service.interfaces;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.request.CreateGroupRequest;
import com.ipze.dto.request.CreateRoomRequest;

import java.util.List;

/**
 * change signature of getUserGroups method
 * user-service
 */
public interface ChatRoomService {

    List<ChatRoomDto> getUserChats();

    ChatRoomDto getOrCreateChatRoom(CreateRoomRequest createRoomRequest);

    ChatRoomDto createGroup(CreateGroupRequest createGroupRequest);

    List<ChatRoomDto> getUserGroups();
}