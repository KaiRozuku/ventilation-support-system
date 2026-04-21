package com.ipze.entity;

import com.ipze.enums.ChatType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "chat_rooms")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    private String id;

    private String name;

    private ChatType chatType;

    private String createdBy;

    private LocalDateTime createdAt;

    private List<ChatParticipant> participants;
}