package com.ipze.repository;


import com.ipze.entity.ChatRoom;
import com.ipze.enums.ChatType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    List<ChatRoom> findByParticipantsUserId(String userId);

    List<ChatRoom> findByChatType(ChatType type);

    @Query("""
        
            {
          'chatType': 'PRIVATE',
          'participants.userId': { $all: [?0, ?1] }
        }
        """)
    Optional<ChatRoom> findPrivateChat(String senderId, String receiverId);

    Optional<ChatRoom> findByIdAndParticipantsUserId(String chatId, String userId);

    boolean existsByIdAndParticipantsUserId(String chatId, String userId);
}