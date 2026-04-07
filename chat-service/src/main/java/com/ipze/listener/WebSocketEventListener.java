package com.ipze.listener;

import com.ipze.dto.ChatMessageDto;
import com.ipze.dto.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent ignored) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttrs = headerAccessor.getSessionAttributes();

        if (sessionAttrs == null || !sessionAttrs.containsKey("username")) {
            log.warn("Session attributes missing or username not set");
            return;
        }

        String username = (String) sessionAttrs.get("username");

        log.info("User Disconnected : {}", username);

        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setMessageType(MessageType.LEAVE);
        chatMessageDto.setSenderId(username);

        messagingTemplate.convertAndSend("/topic/public", chatMessageDto);
    }
}