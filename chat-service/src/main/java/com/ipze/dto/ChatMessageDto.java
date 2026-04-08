package com.ipze.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto implements Serializable {

    private String id;

    private String content;

    private LocalDateTime timestamp;

    private String roomId;

    private String senderId;

    private String receiverId;

    private MessageType messageType;
}