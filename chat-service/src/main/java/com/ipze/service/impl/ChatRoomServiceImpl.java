package com.ipze.service.impl;

import com.ipze.dto.ChatRoomDto;
import com.ipze.dto.ChatType;
import com.ipze.entity.ChatRoom;
import com.ipze.mapper.ChatRoomMapper;
import com.ipze.repository.ChatRoomRepository;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * chat-service
 */
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    public ChatRoomDto getOrCreatePrivateChat(String senderId, String receiverId) {

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot create chat with yourself");
        }

        Optional<ChatRoom> existing = chatRoomRepository
                .findAll()
                .stream()
                .filter(chat ->
                        chat.getChatType() == ChatType.PRIVATE &&
                                chat.getParticipantIds().contains(senderId) &&
                                chat.getParticipantIds().contains(receiverId)
                )
                .findFirst();

        if (existing.isPresent()) {
            return chatRoomMapper.toDto(existing.get());
        }

        ChatRoom chat = ChatRoom.builder()
                .id(generateChatId(senderId, receiverId))
                .chatType(ChatType.PRIVATE)
                .participantIds(List.of(senderId, receiverId))
                .createdAt(LocalDateTime.now())
                .createdBy(senderId)
                .build();

        return chatRoomMapper.toDto(chatRoomRepository.save(chat));
    }

    @Override
    public ChatRoomDto createGroup(List<String> users, String name, String creator) {

        return chatRoomMapper.toDto(
                chatRoomRepository.save(
                        ChatRoom.builder()
                                .id(UUID.randomUUID().toString())
                                .participantIds(users)
                                .chatType(ChatType.PRIVATE)
                                .name(name)
                                .createdBy(creator)
                                .createdAt(LocalDateTime.now())
                                .build()
                ));
    }

    private List<ChatRoomDto> getChatsByType(String userId, ChatType chatType) {
        return chatRoomRepository.findByParticipantIdsContainsAndChatType(userId, chatType)
                .stream()
                .map(chatRoomMapper::toDto)
                .toList();
    }

    private String generateChatId(String u1, String u2) {

        String user1 = u1.trim().toLowerCase();
        String user2 = u2.trim().toLowerCase();

        String raw = user1.compareTo(user2) > 0
                ? user2 + "_" + user1
                : user1 + "_" + user2;

        return UUID.nameUUIDFromBytes(raw.getBytes(StandardCharsets.UTF_8))
                .toString();
    }

    @Override
    public List<ChatRoomDto> getUserPrivateChats(String userId) {
        return getChatsByType(userId, ChatType.PRIVATE);

    }

    @Override
    public List<ChatRoomDto> getUserGroups(String userId) {
        return getChatsByType(userId, ChatType.GROUP);
    }
}