package com.ipze.mapper;

import com.ipze.dto.ChatRoomDto;
import com.ipze.entity.ChatRoom;
import org.mapstruct.Mapper;

@Mapper
public interface ChatRoomMapper {

    ChatRoomDto toDto(ChatRoom chatRoom);
}