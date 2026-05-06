package com.ipze.service.impl;

import com.ipze.dto.ChatParticipantDto;
import com.ipze.entity.ChatParticipant;
import com.ipze.enums.ParticipantRole;
import com.ipze.exceptions.ParticipantNotFoundException;
import com.ipze.mapper.ChatParticipantMapper;
import com.ipze.repository.ChatParticipantRepository;
import com.ipze.service.interfaces.ChatParticipantStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ChatParticipantStateServiceImpl implements ChatParticipantStateService {

    private final ChatParticipantMapper chatParticipantMapper;
    private final ChatParticipantRepository chatParticipantRepository;

    @Override
    @PreAuthorize("@chatSecurityService.isChatAdmin(#chatId, authentication.principal.uuid)")
    public ChatParticipantDto updateRole(String chatId, String userId, ParticipantRole role) {

        ChatParticipant participant = getParticipantOrThrow(chatId, userId);
        participant.setRole(role);

        return chatParticipantMapper.toDto(chatParticipantRepository.save(participant));
    }

    @Override
    @PreAuthorize("@chatSecurityService.isChatAdmin(#chatId, authentication.principal.uuid)")
    public ChatParticipantDto mute(String chatId, String userId, boolean muted) {

        ChatParticipant participant = getParticipantOrThrow(chatId, userId);
        participant.setMuted(muted);

        return chatParticipantMapper.toDto(chatParticipantRepository.save(participant));
    }

    private ChatParticipant getParticipantOrThrow(String chatId, String userId) {
        return chatParticipantRepository
                .findByChatIdAndUserId(chatId, userId)
                .orElseThrow(ParticipantNotFoundException::new);
    }
}