package com.ipze.repository;

import com.ipze.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findAllByReceiverIdOrderByTimestampAsc(String receiverId);

    @Query("""
    {
        $or: [
            { senderId: ?0, receiverId: ?1 },
            { senderId: ?1, receiverId: ?0 }
        ]
    }
""")
    List<ChatMessage> findChat(String user1, String user2);

    List<ChatMessage> findAllByRoomIdOrderByTimestampAsc(String roomId);
}