package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.request.SendMessageRequest;
import com.ipze.entity.ChatMessage;
import com.ipze.entity.ChatParticipant;
import com.ipze.entity.ChatRoom;
import com.ipze.enums.MessageStatus;
import com.ipze.exceptions.MessageNotFoundException;
import com.ipze.exceptions.ParticipantNotFoundException;
import com.ipze.mapper.ChatMessageMapper;
import com.ipze.repository.ChatMessageRepository;
import com.ipze.repository.ChatParticipantRepository;
import com.ipze.repository.ChatRoomRepository;
import com.ipze.service.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final ChatParticipantRepository chatParticipantRepository;


    @Override
    public ChatMessageDto sendMessage(SendMessageRequest request, String userId) {

        ChatRoom chatRoom = chatRoomRepository.findById(request.chatId())
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        ChatParticipant chatParticipant = chatParticipantRepository
                .findByChatIdAndUserId(chatRoom.getId(), userId)
                .orElseThrow(ParticipantNotFoundException::new);

        ChatMessage message = ChatMessage.builder()
                .id(UUID.randomUUID().toString())
                .chatId(request.chatId())
                .senderId(chatParticipant.getUserId())
                .content(request.content())
                .timestamp(LocalDateTime.now())
                .status(MessageStatus.SENT)
                .build();

        return chatMessageMapper.toDto(chatMessageRepository.save(message));
    }

    @Override
    public List<ChatMessageDto> getMessagesOrderByTimestampAsc(String chatId, String userId) {
        validateAccess(chatId, userId);
        return chatMessageRepository.findByChatIdAndStatusNotOrderByTimestampAsc(
                        chatId,
                        MessageStatus.DELETED
                )
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }

    @Override
    public List<ChatMessageDto> getUnreadMessages(String chatId, String userId) {
        return chatMessageRepository.findByChatIdAndSenderIdNotAndStatus(
                        chatId,
                        userId,
                        MessageStatus.SENT
                )
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }

    @Override
    public ChatMessageDto updateMessage(String messageId, String userId, String content) {
        ChatMessage chatMessage = getChatMessage(messageId);

        if (!chatMessage.getSenderId().equals(userId)) {
            throw new AccessDeniedException("No access to message");
        }

        chatMessage.setContent(content);
        chatMessage.setStatus(MessageStatus.EDITED);

        return chatMessageMapper.toDto(chatMessageRepository.save(chatMessage));
    }

    @Override
    public ChatMessageDto deleteMessage(String messageId, String userId) {
        ChatMessage chatMessage = getChatMessage(messageId);

        if (!chatMessage.getSenderId().equals(userId)) {
            throw new AccessDeniedException("No access to message");
        }

        chatMessage.setStatus(MessageStatus.DELETED);
        return  chatMessageMapper.toDto(chatMessageRepository.save(chatMessage));
    }

    @Override
    public ChatMessageDto getLastMessage(String chatId) {
        return chatMessageRepository.findTopByChatIdOrderByTimestampDesc(chatId)
                .map(chatMessageMapper::toDto)
                .orElseThrow(MessageNotFoundException::new);
    }

    @Override
    public List<ChatMessageDto> searchMessages(String chatId, String keyword) {
        return chatMessageRepository.findByChatIdAndContentContainingIgnoreCase(chatId, keyword)
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }

    @Override
    public ChatMessageDto markAsRead(ChatMessageDto message, String userId) {
        validateAccess(message.getChatId(), userId);
        message.setStatus(MessageStatus.READ);
        return message;
    }

    private void validateAccess(String chatId, String userId) {
        boolean hasAccess = chatRoomRepository.existsByIdAndParticipantsUserId(chatId, userId);

        if (!hasAccess) {
            throw new AccessDeniedException("No access to chat");
        }
    }

    private ChatMessage getChatMessage(String messageId){
        return chatMessageRepository.findById(messageId)
                .orElseThrow(MessageNotFoundException::new);
    }
}