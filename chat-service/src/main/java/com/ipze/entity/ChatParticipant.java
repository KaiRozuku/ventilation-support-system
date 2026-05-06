package com.ipze.entity;

import com.ipze.enums.ParticipantRole;
import com.ipze.enums.ParticipantStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("chat_participants")
public class ChatParticipant {

    @Id
    private String id;

    private String chatId;
    private String userId;

    private ParticipantRole role;

    private ParticipantStatus status;

    private LocalDateTime joinedAt;

    private LocalDateTime lastReadAt;
    private LocalDateTime removedAt;
    private LocalDateTime leftAt;

    private boolean muted;
}