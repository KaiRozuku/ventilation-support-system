package com.ipze.service.impl;

import com.ipze.dto.ChatMessageDto;
import com.ipze.service.interfaces.ChatMessageQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageQueryServiceImpl implements ChatMessageQueryService {

    private final WebClientUtils webClientUtils;

    @Override
    public List<ChatMessageDto> getHistory(String senderId, String receiverId, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<List<ChatMessageDto>>(){},
                String.format("/chat-service/chat/api/users/%s/messages/%s", senderId, receiverId),
                httpServletRequest
        ).block();
    }

    @Override
    public List<ChatMessageDto> getRoomMessages(String roomId, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                        new ParameterizedTypeReference<List<ChatMessageDto>>() {},
                        String.format("/rooms/%s/messages", roomId),
                        httpServletRequest
                )
                .block();
    }

    @Override
    public List<ChatMessageDto> getIncomingMessages(String userId, HttpServletRequest httpServletRequest) {
        return webClientUtils.sendGetRequest(
                        new ParameterizedTypeReference<List<ChatMessageDto>>() {},
                        String.format("/users/%s/messages/incoming", userId),
                        httpServletRequest
                )
                .block();
    }
}