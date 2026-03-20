//package com.ipze.domain.postgres;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//import org.hibernate.annotations.UuidGenerator;
//
//import java.util.UUID;
//
//@Data
//@RequiredArgsConstructor
//@SuperBuilder
//@Entity
//@Table(name = "users")
//public class User {
//    @Id
//    @UuidGenerator
//    private UUID userID;
//
//    @Column(nullable = false)
//    private String nameUKR;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Role1 role;
//}