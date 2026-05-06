package com.ipze.client.impl;

import com.ipze.client.interfaces.ChatParticipantClient;
import com.ipze.dto.ParticipantRole;
import com.ipze.dto.request.AddParticipantRequest;
import com.ipze.dto.request.ChatParticipantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatParticipantClientImpl implements ChatParticipantClient {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<List<ChatParticipantDto>> getParticipants(String chatId) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("chat/{chatId}/participants")
                        .buildAndExpand(chatId)
                        .toUriString(),
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<ChatParticipantDto> addParticipant(String chatId, AddParticipantRequest addParticipantRequest) {
        return webClientUtils.sendPostRequest(
                UriComponentsBuilder
                        .fromPath("chat/{chatId}/participants")
                        .queryParam("userId", addParticipantRequest.userId())
                        .queryParam("role", addParticipantRequest.participantRole())
                        .buildAndExpand(chatId)
                        .toUriString(),
                new Object(),
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<ChatParticipantDto> updateRole(String chatId, String userId, ParticipantRole role) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("chat/{chatId}/participants/{userId}/role")
                        .queryParam("role", role)
                        .buildAndExpand(chatId, userId)
                        .toUriString(),
                role,
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<ChatParticipantDto> muteParticipant(String chatId, String userId, boolean muted) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("chat/{chatId}/participants/{userId}/mute")
                        .queryParam("muted", muted)
                        .buildAndExpand(chatId, userId)
                        .toUriString(),
                muted,
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<ChatParticipantDto> removeParticipant(String chatId, String userId) {
        return webClientUtils.sendDeleteRequest(
                UriComponentsBuilder
                        .fromPath("chat/{chatId}/participants/{userId}")
                        .buildAndExpand(chatId, userId)
                        .toUriString(),
                ChatParticipantDto.class
        );
    }
}