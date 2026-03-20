package com.ipze.model.postgres;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @UuidGenerator
    private UUID userID;

    @Column(nullable = false)
    private String firstNameUKR;

    @Column(nullable = false)
    private String lastNameUKR;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.UuidGenerator;
//
//import java.util.UUID;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
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
//    private Role role;
//}