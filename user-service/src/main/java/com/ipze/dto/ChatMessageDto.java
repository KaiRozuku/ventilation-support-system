package com.ipze.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {

    private String id;

    private String content;

    private String senderId;

    private String receiverId;

    private String roomId;

    private LocalDateTime timestamp;

    private MessageType messageType;
}