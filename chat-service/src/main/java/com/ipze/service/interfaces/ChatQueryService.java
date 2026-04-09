package com.ipze.service.interfaces;

import com.ipze.entity.ChatMessage;

import java.util.List;


public interface ChatQueryService {

    List<ChatMessage> getMessagesFromChat(String senderId, String receiverId);

    List<ChatMessage> getUserIncomingMessages(String receiverId);

    List<ChatMessage> getMessagesFromGroup(String roomId);
}