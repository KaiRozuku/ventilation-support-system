package com.ipze.service.interfaces;

import com.ipze.dto.ChatRoomDto;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoomDto> getUserPrivateChats(String userId);

    ChatRoomDto getOrCreatePrivateChat(String senderId, String receiverId);

    List<ChatRoomDto> getUserGroups(String userId);

    ChatRoomDto createGroup(List<String> users, String name, String creatorId);

    ChatRoomDto getChatRoom(String chatId, String userId);

    void deleteChat(String chatId, String userId);
}