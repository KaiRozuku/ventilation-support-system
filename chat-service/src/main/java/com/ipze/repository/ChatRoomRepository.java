package com.ipze.repository;

import com.ipze.entity.ChatRoom;
import com.ipze.enums.ChatType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    List<ChatRoom> findByChatType(ChatType chatType);
}