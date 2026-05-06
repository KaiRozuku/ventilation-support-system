package com.ipze.repository;

import com.ipze.entity.ChatMessage;
import com.ipze.enums.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    Optional<ChatMessage> findTopByChatIdAndStatusNotOrderByTimestampDesc(
            String chatId,
            MessageStatus status
    );

    List<ChatMessage> findByChatIdAndContentContainingIgnoreCaseAndStatusNot(
            String chatId,
            String keyword,
            MessageStatus status
    );

    List<ChatMessage> findByChatIdAndStatusNotOrderByTimestampAsc(
            String chatId,
            MessageStatus status
    );

    List<ChatMessage> findByChatIdAndSenderIdNotAndStatus(
            String chatId,
            String senderId,
            MessageStatus status
    );
}