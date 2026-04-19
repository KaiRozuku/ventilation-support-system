package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.entity.MessageStatus;
import com.ipze.mapper.ChatMessageMapper;
import com.ipze.repository.ChatMessageRepository;
import com.ipze.repository.ChatRoomRepository;
import com.ipze.service.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageMapper chatMessageMapper;


    @Override
    public ChatMessageDto saveMessage(ChatMessageDto dto) {
        dto.setTimestamp(LocalDateTime.now());
        dto.setStatus(MessageStatus.DELIVERED);
        return chatMessageMapper.toDto(
                chatMessageRepository.save(
                        chatMessageMapper.toEntity(dto)
                )
        );
    }

    @Override
    public List<ChatMessageDto> getMessages(String chatId, String userId) {

        boolean hasAccess = chatRoomRepository
                .existsByIdAndParticipantIdsContains(chatId, userId);

        if (!hasAccess) {
            throw new AccessDeniedException("User is not a participant of this chat");
        }

        return chatMessageRepository
                .findByChatIdOrderByTimestampAsc(chatId)
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }


    @Override
    public List<ChatMessageDto> getUnreadMessages(String chatId, String userId) {
        return chatMessageRepository.findByChatIdAndSenderIdNotAndStatusOrderByTimestamp(
                chatId,
                userId,
                MessageStatus.RECEIVED
        )
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }
}