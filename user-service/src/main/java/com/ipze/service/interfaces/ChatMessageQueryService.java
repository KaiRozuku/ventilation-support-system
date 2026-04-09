package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ChatMessageQueryService {

    List<ChatMessageDto> getHistory(String senderId, String receiverId, HttpServletRequest httpServletRequest);

    List<ChatMessageDto> getRoomMessages(@PathVariable String roomId, HttpServletRequest httpServletRequest);

    List<ChatMessageDto> getIncomingMessages(@PathVariable String userId, HttpServletRequest httpServletRequest);
}