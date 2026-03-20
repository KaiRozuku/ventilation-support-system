//package com.ipze.domain.postgres;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.annotations.UuidGenerator;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Data
//@RequiredArgsConstructor
//
//public class RevokedToken {
//    @Id
//    @UuidGenerator
//    private UUID uuid;
//
//    private String token;
//
//    private LocalDateTime revokedAt;
//
//    public RevokedToken(String token, LocalDateTime revokedAt) {
//        this.token = token;
//        this.revokedAt = revokedAt;
//    }
//}