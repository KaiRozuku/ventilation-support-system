package com.ipze.repository;

import com.ipze.dto.ChatType;
import com.ipze.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    @NonNull
    Optional<ChatRoom> findById(@NonNull String id);

    boolean existsByIdAndParticipantIdsContains(String id, String participantId);

    List<ChatRoom> findByParticipantIdsContainsAndChatType(String userId, ChatType chatType);
}