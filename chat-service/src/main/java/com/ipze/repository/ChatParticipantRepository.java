package com.ipze.repository;

import com.ipze.entity.ChatParticipant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepository extends MongoRepository<ChatParticipant, String> {

    List<ChatParticipant> findByChatId(String chatId);

    List<ChatParticipant> findByUserId(String userId);

    List<ChatParticipant> findByChatIdIn(Collection<String> chatIds);

    Optional<ChatParticipant> findByChatIdAndUserId(String chatId, String userId);

    boolean existsByChatIdAndUserId(String chatId, String userId);

    void deleteByChatIdAndUserId(String chatId, String userId);
}