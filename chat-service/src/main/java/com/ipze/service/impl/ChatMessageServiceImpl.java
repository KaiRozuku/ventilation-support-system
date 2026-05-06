package com.ipze.service.impl;


import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.request.SendMessageRequest;
import com.ipze.entity.ChatMessage;
import com.ipze.enums.MessageStatus;
import com.ipze.exceptions.ChatRoomNotFoundException;
import com.ipze.exceptions.MessageNotFoundException;
import com.ipze.mapper.ChatMessageMapper;
import com.ipze.repository.ChatMessageRepository;
import com.ipze.repository.ChatRoomRepository;
import com.ipze.service.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#request.chatId(), #userId)")
    public ChatMessageDto savedMessage(
            SendMessageRequest request,
            String userId
    ) {

        chatRoomRepository.findById(request.chatId())
                .orElseThrow(ChatRoomNotFoundException::new);

        ChatMessage message = ChatMessage.builder()
                .id(UUID.randomUUID().toString())
                .chatId(request.chatId())
                .senderId(userId)
                .content(request.content())
                .timestamp(LocalDateTime.now())
                .status(MessageStatus.SENT)
                .build();

        ChatMessage saved = chatMessageRepository.save(message);

        return chatMessageMapper.toDto(saved);
    }

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, #userId)")
    public List<ChatMessageDto> getMessagesOrderByTimestampAsc(
            String chatId,
            String userId
    ) {

        return chatMessageRepository
                .findByChatIdAndStatusNotOrderByTimestampAsc(
                        chatId,
                        MessageStatus.DELETED
                )
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, #userId)")
    public List<ChatMessageDto> getUnreadMessages(
            String chatId,
            String userId
    ) {

        return chatMessageRepository
                .findByChatIdAndSenderIdNotAndStatus(
                        chatId,
                        userId,
                        MessageStatus.SENT
                )
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }

    @Override
    @PreAuthorize("@messageSecurityService.canEditMessage(#messageId, #userId)")
    public ChatMessageDto updateMessage(
            String messageId,
            String userId,
            String content
    ) {

        ChatMessage message = getChatMessage(messageId);

        message.setContent(content);
        message.setStatus(MessageStatus.EDITED);

        ChatMessage updated = chatMessageRepository.save(message);

        return chatMessageMapper.toDto(updated);
    }

    @Override
    @PreAuthorize("@messageSecurityService.isMessageOwner(#messageId, #userId)")
    public ChatMessageDto deleteMessage(
            String messageId,
            String userId
    ) {

        ChatMessage message = getChatMessage(messageId);
        message.setStatus(MessageStatus.DELETED);

        ChatMessage deleted = chatMessageRepository.save(message);
        return chatMessageMapper.toDto(deleted);
    }

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.name)")
    public ChatMessageDto getLastMessage(String chatId) {

        return chatMessageRepository
                .findTopByChatIdAndStatusNotOrderByTimestampDesc(
                        chatId,
                        MessageStatus.DELETED
                )
                .map(chatMessageMapper::toDto)
                .orElseThrow(MessageNotFoundException::new);
    }

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#chatId, authentication.name)")
    public List<ChatMessageDto> searchMessages(
            String chatId,
            String keyword
    ) {

        return chatMessageRepository
                .findByChatIdAndContentContainingIgnoreCaseAndStatusNot(
                        chatId,
                        keyword,
                        MessageStatus.DELETED
                )
                .stream()
                .map(chatMessageMapper::toDto)
                .toList();
    }

    @Override
    @PreAuthorize("@chatSecurityService.isParticipant(#message.chatId, #userId)")
    public ChatMessageDto markAsRead(
            ChatMessageDto message,
            String userId
    ) {

        ChatMessage entity = getChatMessage(message.getId());
        entity.setStatus(MessageStatus.READ);

        ChatMessage updated = chatMessageRepository.save(entity);
        return chatMessageMapper.toDto(updated);
    }

    private ChatMessage getChatMessage(String messageId) {
        return chatMessageRepository.findById(messageId)
                .orElseThrow(MessageNotFoundException::new);
    }
}