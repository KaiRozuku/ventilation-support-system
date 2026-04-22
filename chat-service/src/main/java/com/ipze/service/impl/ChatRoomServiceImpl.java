package com.ipze.service.impl;

import com.ipze.dto.ChatRoomDto;
import com.ipze.entity.ChatParticipant;
import com.ipze.entity.ChatRoom;
import com.ipze.enums.ChatType;
import com.ipze.enums.ParticipantRole;
import com.ipze.exceptions.ChatNotFoundException;
import com.ipze.exceptions.ParticipantNotFoundException;
import com.ipze.mapper.ChatRoomMapper;
import com.ipze.repository.ChatParticipantRepository;
import com.ipze.repository.ChatRoomRepository;
import com.ipze.service.WebClientService;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;
    private final WebClientService webClientService;
    private final ChatParticipantRepository chatParticipantRepository;

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
        log.info("receiverId -> {}", receiverId);
        if (!webClientService.validateUserExists(receiverId)) {
            throw new ParticipantNotFoundException();
        }

        return chatRoomRepository.findPrivateChat(senderId, receiverId)
                .map(chatRoomMapper::toDto)
                .orElseGet(() -> {

                    String chatId = generateChatId(senderId, receiverId);

                    return chatRoomMapper.toDto(
                            chatRoomRepository.save(
                            ChatRoom.builder()
                                    .chatType(ChatType.PRIVATE)
                                    .id(chatId)
                                    .createdBy(senderId)
                                    .createdAt(LocalDateTime.now())
                                    .participants(
                                            chatParticipantRepository.saveAll(List.of(
                                                    ChatParticipant.builder()
                                                            .chatId(chatId)
                                                            .userId(senderId)
                                                            .build(),
                                                    ChatParticipant.builder()
                                                            .chatId(chatId)
                                                            .userId(receiverId)
                                                            .build()
                                            ))
                                    )
                                    .build()
                        )
                    );
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

        List<String> invalidUsers = uniqueUsers.stream()
                .filter(x -> !webClientService.validateUserExists(x))
                .toList();

        if (!invalidUsers.isEmpty()) {
            throw new IllegalArgumentException("Some users do not exist: " + invalidUsers);
        }

        String chatId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        ChatRoom chatRoom = ChatRoom.builder()
                .id(chatId)
                .chatType(ChatType.GROUP)
                .name(name)
                .createdBy(creatorId)
                .createdAt(now)
                .participants(
                        chatParticipantRepository.saveAll(
                        uniqueUsers.stream()
                                .map(userId -> ChatParticipant.builder()
                                        .chatId(chatId)
                                        .userId(userId)
                                        .joinedAt(now)
                                        .role(userId.equals(creatorId)
                                                ? ParticipantRole.ADMIN
                                                : ParticipantRole.MEMBER)
                                        .build()
                                )
                                .toList()
                ))
                .build();

        return chatRoomMapper.toDto(chatRoomRepository.save(chatRoom));
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

    private String generateChatId(String u1, String u2) {

        String user1 = u1.trim().toLowerCase();
        String user2 = u2.trim().toLowerCase();

        String raw = user1.compareTo(user2) > 0
                ? user2 + "_" + user1
                : user1 + "_" + user2;

        return UUID.nameUUIDFromBytes(raw.getBytes(StandardCharsets.UTF_8))
                .toString();
    }
}