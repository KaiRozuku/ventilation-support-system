package com.ipze.service.impl;

import com.ipze.dto.ChatParticipantDto;
import com.ipze.entity.ChatParticipant;
import com.ipze.enums.ParticipantRole;
import com.ipze.enums.ParticipantStatus;
import com.ipze.exceptions.ParticipantNotFoundException;
import com.ipze.mapper.ChatParticipantMapper;
import com.ipze.repository.ChatParticipantRepository;
import com.ipze.repository.ChatRoomRepository;
import com.ipze.service.interfaces.ChatParticipantManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatParticipantManagementServiceImpl implements ChatParticipantManagementService {

    private final ChatParticipantRepository chatParticipantRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantMapper chatParticipantMapper;

    @Override
    public ChatParticipantDto addParticipant(String chatId, String userId, ParticipantRole role) {

        ChatParticipant participant = ChatParticipant.builder()
                .chatId(chatId)
                .userId(userId)
                .role(role)
                .muted(false)
                .joinedAt(LocalDateTime.now())
                .build();

        return chatParticipantMapper.toDto(chatParticipantRepository.save(participant));
    }

    @Override
    public void removeParticipant(String chatId, String userId) {

        ChatParticipant participant = chatParticipantRepository
                .findByChatIdAndUserId(chatId, userId)
                .orElseThrow(ParticipantNotFoundException::new);

        participant.setStatus(ParticipantStatus.REMOVED);
        participant.setRemovedAt(LocalDateTime.now());
        chatParticipantRepository.deleteByChatIdAndUserId(chatId, userId);
    }

    @Override
    public void leaveChat(String chatId, String userId) {

        ChatParticipant participant = chatParticipantRepository
                .findByChatIdAndUserId(chatId, userId)
                .orElseThrow(ParticipantNotFoundException::new);

        participant.setStatus(ParticipantStatus.LEFT);
        participant.setLeftAt(LocalDateTime.now());

        chatParticipantRepository.save(participant);
    }
}