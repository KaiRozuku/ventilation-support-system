package com.ipze.mapper;

import com.ipze.dto.ChatMessageDto;
import com.ipze.entity.ChatMessage;
import org.mapstruct.Mapper;


@Mapper
public interface ChatMessageMapper {

    ChatMessageDto toDto(ChatMessage chatMessage);

    ChatMessage toEntity(ChatMessageDto chatMessageDto);
}