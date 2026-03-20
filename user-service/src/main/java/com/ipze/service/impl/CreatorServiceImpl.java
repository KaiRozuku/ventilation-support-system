//package com.ipze.service.impl;
//import com.ipze.domain.postgres.Role1;
//import com.ipze.domain.postgres.User1;
//import com.ipze.exception.UserNotFoundException;
//import com.ipze.repository.repository.UserRepository2;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class CreatorServiceImpl{
//
//    private final UserRepository2 userRepository2;
//
//
//    public Page<User1> getUsersByRole(Role1 role, Pageable pageable) {
//        return userRepository2.findAllByRole(role, pageable);
//    }
//
//    public User1 getUserByEmail(String email) {
//        return userRepository2.findUserByEmail(email)
//                .orElseThrow(UserNotFoundException::new);
//    }
//
//    public void changeRoleOfUser(UUID uuid, Role1 role) {
//
//    }
//
//    @Transactional
//    public void changeRoleOfUser(UUID uuid, Role1 role) {
//        User1 user = userRepository2.findById(uuid)
//                .orElseThrow(UserNotFoundException::new);
//        user.setRole(role);
//    }
//}