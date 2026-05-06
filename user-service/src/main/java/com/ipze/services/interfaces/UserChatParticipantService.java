package com.ipze.services.interfaces;

import com.ipze.dto.ParticipantRole;
import com.ipze.dto.request.AddParticipantRequest;
import com.ipze.dto.request.ChatParticipantDto;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserChatParticipantService {

    Mono<List<ChatParticipantDto>> getParticipants(String chatId);

    Mono<ChatParticipantDto> addParticipant(String chatId, AddParticipantRequest addParticipantRequest);

    Mono<ChatParticipantDto> updateRole(String chatId, String userId, ParticipantRole role);

    Mono<ChatParticipantDto> muteParticipant(String chatId, String userId, boolean muted);

    Mono<ChatParticipantDto> removeParticipant(String chatId, String userId);
}