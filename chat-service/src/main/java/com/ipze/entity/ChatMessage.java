package com.ipze.entity;

import com.ipze.dto.MessageType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Document(collection = "chat_messages")
@Builder
public class ChatMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    private String content;

    @CreatedDate
    private LocalDateTime timestamp;

    private  String roomId;

    private String senderId;

    private String receiverId;

    private MessageType messageType;
}