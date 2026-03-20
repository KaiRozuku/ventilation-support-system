package com.ipze.model.redis;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(value = "Tokens", timeToLive = 86400)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private String id;

    private String username;
    private String authenticationToken;
    private String modifiedBy;
    private LocalDateTime modifiedOn;
    private String createdBy;
    private LocalDateTime createdOn;
}