package com.ipze.model.redis;//package com.ipze.model.redis;
//
//import jakarta.persistence.Id;
//import lombok.*;
//import org.springframework.data.redis.core.RedisHash;
//import org.springframework.data.redis.core.TimeToLive;
//
//import java.io.Serializable;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@RedisHash(value = "Tokens", timeToLive = 86400)
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Token {
//
//    private String id;
//
//    private String username;
//    private String authenticationToken;
//    private String modifiedBy;
//    private LocalDateTime modifiedOn;
//    private String createdBy;
//    private LocalDateTime createdOn;
//
//}

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@RedisHash("refresh_token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {

    @Id
    private UUID id;

    @Indexed
    private String token;

    private Instant expiryDate;

    private String userId;

    @TimeToLive
    private Long ttl;
}