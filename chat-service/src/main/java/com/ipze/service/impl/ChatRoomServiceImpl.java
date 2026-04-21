package com.ipze.service.impl;

import com.ipze.dto.ChatRoomDto;
import com.ipze.entity.ChatParticipant;
import com.ipze.entity.ChatRoom;
import com.ipze.enums.ChatType;
import com.ipze.enums.ParticipantRole;
import com.ipze.exceptions.ChatNotFoundException;
import com.ipze.mapper.ChatRoomMapper;
import com.ipze.repository.ChatRoomRepository;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    public List<ChatRoomDto> getUserPrivateChats(String userId) {

        return chatRoomRepository.findByParticipantsUserId(userId)
                .stream()
                .filter(chat -> chat.getChatType() == ChatType.PRIVATE)
                .map(chatRoomMapper::toDto)
                .toList();
    }

    @Override
    public ChatRoomDto getOrCreatePrivateChat(String senderId, String receiverId) {

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot create chat with yourself");
        }

        return chatRoomRepository.findPrivateChat(senderId, receiverId)
                .map(chatRoomMapper::toDto)
                .orElseGet(() -> {

                    ChatRoom chat = ChatRoom.builder()
                            .chatType(ChatType.PRIVATE)
                            .createdAt(LocalDateTime.now())
                            .build();

                    ChatRoom saved = chatRoomRepository.save(chat);

                    saved.setParticipants(
                            List.of(
                            ChatParticipant.builder()
                                    .chatId(saved.getId())
                                    .userId(senderId)
                                    .build(),
                            ChatParticipant.builder()
                                    .chatId(saved.getId())
                                    .userId(receiverId)
                                    .build()
                    ));
                    chatRoomRepository.save(saved);

                    return chatRoomMapper.toDto(saved);
                });
    }

    @Override
    public List<ChatRoomDto> getUserGroups(String userId) {
        return chatRoomRepository.findByChatType(ChatType.GROUP)
                .stream()
                .filter(chat -> chat.getParticipants().stream()
                        .anyMatch(p -> p.getUserId().equals(userId)))
                .map(chatRoomMapper::toDto)
                .toList();
    }

    @Override
    public ChatRoomDto createGroup(List<String> users, String name, String creatorId) {

        if (users == null || users.isEmpty()) {
            throw new IllegalArgumentException("Group must have at least one participant");
        }

        Set<String> uniqueUsers = new LinkedHashSet<>(users);
        uniqueUsers.add(creatorId);

        ChatRoom saved = chatRoomRepository.save(
                ChatRoom.builder()
                        .chatType(ChatType.GROUP)
                        .name(name)
                        .createdBy(creatorId)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        List<ChatParticipant> participants = uniqueUsers.stream()
                .map(userId -> ChatParticipant.builder()
                        .chatId(saved.getId())
                        .userId(userId)
                        .role(userId.equals(creatorId)
                                ? ParticipantRole.ADMIN
                                : ParticipantRole.MEMBER)
                        .build()
                )
                .toList();

        saved.setParticipants(participants);

        ChatRoom finalChat = chatRoomRepository.save(saved);

        return chatRoomMapper.toDto(finalChat);
    }

    @Override
    public ChatRoomDto getChatRoom(String chatId, String userId) {
        return chatRoomMapper.toDto(getChatRoomOrThrow(chatId, userId));
    }

    private ChatRoom getChatRoomOrThrow(String chatId, String userId) {
        return chatRoomRepository.findByIdAndParticipantsUserId(chatId, userId)
                .orElseThrow(ChatNotFoundException::new);
    }

    @Override
    public void deleteChat(String chatId, String userId) {
        ChatRoom chat = getChatRoomOrThrow(chatId, userId);

        if (!chat.getCreatedBy().equals(userId)) {
            throw new AccessDeniedException("Only owner can delete chat");
        }

        chatRoomRepository.delete(chat);
    }
}