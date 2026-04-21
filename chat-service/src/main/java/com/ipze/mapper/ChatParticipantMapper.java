package com.ipze.mapper;

import com.ipze.dto.ChatParticipantDto;
import com.ipze.entity.ChatParticipant;
import org.mapstruct.Mapper;


@Mapper
public interface ChatParticipantMapper {

    ChatParticipantDto toDto(ChatParticipant chatParticipant);
}