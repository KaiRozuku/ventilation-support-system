package com.ipze.entity;

import com.ipze.enums.ParticipantRole;
import com.ipze.enums.ParticipantStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_participant")
public class ChatParticipant {

    @Id
    private String id;

    private String chatId;

    private String userId;

    private ParticipantRole role;

    @CreatedDate
    private LocalDateTime joinedAt;

    private LocalDateTime lastReadAt;

    private ParticipantStatus status;

    private LocalDateTime leftAt;

    private LocalDateTime removedAt;

    private boolean muted;
}