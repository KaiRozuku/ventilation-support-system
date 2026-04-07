package com.ipze.service.interfaces;

import com.ipze.entity.ChatMessage;

import java.util.List;

public interface ChatQueryService {

    List<ChatMessage> getHistory(String senderId, String receiverId);

    List<ChatMessage> getUserInbox(String receiverId);

    List<ChatMessage> getByRoom(String roomId);
}