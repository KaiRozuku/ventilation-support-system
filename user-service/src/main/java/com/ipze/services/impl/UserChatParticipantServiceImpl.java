package com.ipze.services.impl;

import com.ipze.client.interfaces.ChatParticipantClient;
import com.ipze.dto.ParticipantRole;
import com.ipze.dto.request.AddParticipantRequest;
import com.ipze.dto.request.ChatParticipantDto;
import com.ipze.services.interfaces.UserChatParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChatParticipantServiceImpl implements UserChatParticipantService {

    private final ChatParticipantClient chatParticipantClient;

    @Override
    public Mono<List<ChatParticipantDto>> getParticipants(String chatId) {
        return chatParticipantClient.getParticipants(chatId);
    }

    @Override
    public Mono<ChatParticipantDto> addParticipant(String chatId, AddParticipantRequest addParticipantRequest) {
        return chatParticipantClient
                .addParticipant(chatId, addParticipantRequest);
    }

    @Override
    public Mono<ChatParticipantDto> updateRole(String chatId, String userId, ParticipantRole role) {
        return chatParticipantClient.updateRole(chatId, userId, role);
    }

    @Override
    public Mono<ChatParticipantDto> muteParticipant(String chatId, String userId, boolean muted) {
        return chatParticipantClient.muteParticipant(chatId, userId, muted);
    }

    @Override
    public Mono<ChatParticipantDto> removeParticipant(String chatId, String userId) {
        return chatParticipantClient.removeParticipant(chatId, userId);
    }
}