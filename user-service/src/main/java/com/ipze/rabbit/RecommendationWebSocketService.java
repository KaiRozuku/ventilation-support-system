package com.ipze.rabbit;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecommendationWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void send(RecommendationMessage message) {
        messagingTemplate.convertAndSend(
                "/topic/recommendations",
                message
        );
    }
}