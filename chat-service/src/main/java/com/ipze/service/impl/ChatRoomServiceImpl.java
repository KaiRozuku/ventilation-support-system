package com.ipze.service.impl;

import com.ipze.dto.*;
import com.ipze.entity.ChatParticipant;
import com.ipze.entity.ChatRoom;
import com.ipze.enums.ChatType;
import com.ipze.enums.ParticipantRole;
import com.ipze.enums.ParticipantStatus;
import com.ipze.exceptions.ChatNotFoundException;
import com.ipze.exceptions.ParticipantNotFoundException;
import com.ipze.repository.ChatParticipantRepository;
import com.ipze.repository.ChatRoomRepository;
import com.ipze.service.WebClientService;
import com.ipze.service.interfaces.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatParticipantRepository chatParticipantRepository;
    private final WebClientService webClientService;

    @Override
    @Transactional
    public ChatRoomDto createGroup(List<String> users, String name, String creatorId) {

        if (users == null || users.isEmpty()) {
            throw new IllegalArgumentException("Group must have participants");
        }

        Set<String> uniqueUsers = new LinkedHashSet<>(users);
        uniqueUsers.add(creatorId);

        List<UserShortDto> existingUsers =
                webClientService.getUsers(new ArrayList<>(uniqueUsers));

        if (existingUsers.size() != uniqueUsers.size()) {
            throw new ParticipantNotFoundException();
        }

        String chatId = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        ChatRoom chatRoom = chatRoomRepository.save(
                ChatRoom.builder()
                        .id(chatId)
                        .name(name)
                        .chatType(ChatType.GROUP)
                        .createdBy(creatorId)
                        .createdAt(now)
                        .build()
        );

        List<ChatParticipant> participants = uniqueUsers.stream()
                .map(userId -> ChatParticipant.builder()
                        .chatId(chatId)
                        .userId(userId)
                        .role(userId.equals(creatorId)
                                ? ParticipantRole.ADMIN
                                : ParticipantRole.MEMBER)
                        .status(ParticipantStatus.ACTIVE)
                        .joinedAt(now)
                        .muted(false)
                        .build()
                )
                .toList();

        chatParticipantRepository.saveAll(participants);
        chatRoomRepository.save(chatRoom);
        return buildChatRoomDto(chatRoom, participants);
    }

    @Override
    public ChatRoomDto getChatRoom(String chatId, String userId) {

        if (!chatParticipantRepository.existsByChatIdAndUserId(chatId, userId)) {
            throw new ChatNotFoundException();
        }

        ChatRoom chatRoom = chatRoomRepository.findById(chatId)
                .orElseThrow(ChatNotFoundException::new);

        List<ChatParticipant> participants =
                chatParticipantRepository.findByChatId(chatId);

        return buildChatRoomDto(chatRoom, participants);
    }

    @Override
    @Transactional
    public void deleteChat(String chatId, String userId) {

        ChatParticipant participant = chatParticipantRepository
                .findByChatIdAndUserId(chatId, userId)
                .orElseThrow(ChatNotFoundException::new);

        if (participant.getRole() != ParticipantRole.ADMIN) {
            throw new IllegalStateException("Only admin can delete chat");
        }

        chatParticipantRepository.deleteAll(
                chatParticipantRepository.findByChatId(chatId)
        );

        chatRoomRepository.deleteById(chatId);
    }

    @Override
    public ChatRoomDto getOrCreatePrivateChat(String senderId, String receiverId) {

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot chat with yourself");
        }

        List<UserShortDto> users =
                webClientService.getUsers(List.of(receiverId));

        if (users.isEmpty()) {
            throw new ParticipantNotFoundException();
        }

        String chatId = generatePrivateChatId(senderId, receiverId);

        Optional<ChatRoom> existing = chatRoomRepository.findById(chatId);

        if (existing.isPresent()) {
            List<ChatParticipant> participants =
                    chatParticipantRepository.findByChatId(chatId);

            return buildChatRoomDto(existing.get(), participants);
        }

        LocalDateTime now = LocalDateTime.now();

        ChatRoom chatRoom = chatRoomRepository.save(
                ChatRoom.builder()
                        .id(chatId)
                        .chatType(ChatType.PRIVATE)
                        .createdBy(senderId)
                        .createdAt(now)
                        .build()
        );

        List<ChatParticipant> participants = List.of(
                ChatParticipant.builder()
                        .chatId(chatId)
                        .userId(senderId)
                        .role(ParticipantRole.ADMIN)
                        .joinedAt(now)
                        .build(),
                ChatParticipant.builder()
                        .chatId(chatId)
                        .userId(receiverId)
                        .role(ParticipantRole.ADMIN)
                        .joinedAt(now)
                        .build()
        );

        chatParticipantRepository.saveAll(participants);
        chatRoomRepository.save(chatRoom);

        return buildChatRoomDto(chatRoom, participants);
    }

    @Override
    public List<ChatRoomDto> getUserPrivateChats(String userId) {

        List<String> chatIds = chatParticipantRepository.findByUserId(userId)
                .stream()
                .map(ChatParticipant::getChatId)
                .toList();

        return chatRoomRepository.findAllById(chatIds)
                .stream()
                .filter(chat -> chat.getChatType() == ChatType.PRIVATE)
                .map(chat -> buildChatRoomDto(
                        chat,
                        chatParticipantRepository.findByChatId(chat.getId())
                ))
                .toList();
    }

    @Override
    public List<ChatRoomDto> getUserGroups(String userId) {

        List<String> chatIds = chatParticipantRepository.findByUserId(userId)
                .stream()
                .map(ChatParticipant::getChatId)
                .toList();

        return chatRoomRepository.findAllById(chatIds)
                .stream()
                .filter(chat -> chat.getChatType() == ChatType.GROUP)
                .map(chat -> buildChatRoomDto(
                        chat,
                        chatParticipantRepository.findByChatId(chat.getId())
                ))
                .toList();
    }

    private ChatRoomDto buildChatRoomDto(ChatRoom chatRoom,
                                         List<ChatParticipant> participants) {

        List<String> userIds = participants.stream()
                .map(ChatParticipant::getUserId)
                .toList();

        Map<String, UserShortDto> users = webClientService.getUsers(userIds)
                .stream()
                .collect(Collectors.toMap(k -> k.userId().toString(), u -> u));

        List<ChatParticipantDto> participantDtos = participants.stream()
                .map(p -> {
                    UserShortDto user = users.get(p.getUserId());

                    return new ChatParticipantDto(
                            p.getUserId(),
                            user != null ? user.username() : "unknown",
                            user != null ? user.email() : null,
                            user != null ? user.avatarUrl() : null,
                            p.getRole(),
                            p.getJoinedAt()
                    );
                })
                .toList();

        return new ChatRoomDto(
                chatRoom.getId(),
                chatRoom.getName(),
                chatRoom.getChatType(),
                chatRoom.getCreatedBy(),
                chatRoom.getCreatedAt(),
                participants.stream().map(ChatParticipant::getUserId).toList(),
                participantDtos
        );
    }

    private String generatePrivateChatId(String u1, String u2) {
        return UUID.nameUUIDFromBytes(
                Stream.of(u1.trim().toLowerCase(), u2.trim().toLowerCase())
                        .sorted()
                        .collect(Collectors.joining("_"))
                        .getBytes(StandardCharsets.UTF_8)
        ).toString();
    }
}