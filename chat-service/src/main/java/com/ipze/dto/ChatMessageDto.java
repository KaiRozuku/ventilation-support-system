package com.ipze.dto;

import com.ipze.enums.MessageStatus;
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

    private String chatId;

    private String senderId;

    private String content;

    private LocalDateTime timestamp;

    private MessageStatus status;
}