package com.ipze.service.impl;

import com.ipze.dto.ChatParticipantDto;
import com.ipze.entity.ChatParticipant;
import com.ipze.exceptions.ParticipantNotFoundException;
import com.ipze.mapper.ChatParticipantMapper;
import com.ipze.repository.ChatParticipantRepository;
import com.ipze.service.interfaces.ChatParticipantQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatParticipantQueryServiceImpl implements ChatParticipantQueryService {

    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatParticipantMapper chatParticipantMapper;

    @Override
    public List<ChatParticipantDto> getParticipants(String chatId) {
        return chatParticipantRepository.findByChatId(chatId)
                .stream()
                .map(chatParticipantMapper::toDto)
                .toList();
    }

    @Override
    public ChatParticipantDto getParticipant(String chatId, String userId) {
        return chatParticipantMapper.toDto(getParticipantOrThrow(chatId, userId));
    }

    private ChatParticipant getParticipantOrThrow(String chatId, String userId) {
        return chatParticipantRepository
                .findByChatIdAndUserId(chatId, userId)
                .orElseThrow(ParticipantNotFoundException::new);
    }
}