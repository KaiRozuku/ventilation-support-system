package com.ipze.service.impl;

import com.ipze.dto.ChatParticipantDto;
import com.ipze.entity.ChatParticipant;
import com.ipze.exceptions.ParticipantNotFoundException;
import com.ipze.mapper.ChatParticipantMapper;
import com.ipze.repository.ChatParticipantRepository;
import com.ipze.service.interfaces.ChatParticipantQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatParticipantQueryServiceImpl implements ChatParticipantQueryService {

    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatParticipantMapper chatParticipantMapper;

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.principal.uuid)")
    public List<ChatParticipantDto> getParticipants(String chatId) {
        return chatParticipantRepository.findByChatId(chatId)
                .stream()
                .map(chatParticipantMapper::toDto)
                .toList();
    }

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.principal.uuid)")
    public ChatParticipantDto getParticipant(String chatId, String userId) {
        return chatParticipantMapper.toDto(getParticipantOrThrow(chatId, userId));
    }

    private ChatParticipant getParticipantOrThrow(String chatId, String userId) {
        return chatParticipantRepository
                .findByChatIdAndUserId(chatId, userId)
                .orElseThrow(ParticipantNotFoundException::new);
    }
}