package com.ipze.repository;

import com.ipze.entity.ChatParticipant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends MongoRepository<ChatParticipant, String> {

    List<ChatParticipant> findByChatId(String chatId);

    Optional<ChatParticipant> findByChatIdAndUserId(String chatId, String userId);

    void deleteByChatIdAndUserId(String chatId, String userId);
}