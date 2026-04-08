package com.ipze.service.interfaces;

import com.ipze.dto.ChatMessageDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ChatMessageService {

    void sendToUser(ChatMessageDto chatMessageDto);

    List<ChatMessageDto> getHistory(HttpServletRequest httpServletRequest);
}