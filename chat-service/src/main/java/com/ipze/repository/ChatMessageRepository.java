package com.ipze.repository;

import com.ipze.entity.ChatMessage;
import com.ipze.entity.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findByChatIdOrderByTimestampAsc(String chatId);

    List<ChatMessage> findByChatIdAndSenderIdNotAndStatusOrderByTimestamp(
            String chatId,
            String senderId,
            MessageStatus status
    );
}