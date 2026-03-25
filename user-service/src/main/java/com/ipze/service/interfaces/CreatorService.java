//package com.ipze.service.interfaces;
////
////import com.ipze.domain.postgres.Role;
////import com.ipze.domain.postgres.User;
////import org.springframework.data.domain.Page;
////import org.springframework.data.domain.Pageable;
////
////import java.util.UUID;
////
///**
// * Основні методи для class Creator
// * @author vasylnosal
// */
//public interface CreatorService {
//
//    Page<User> getAllUsers(Pageable pageable);
//
//    Page<User> getUsersByRole(Role role, Pageable pageable);
//
//    User getUserByEmail(String email);
//
//    void changeRoleOfUser(UUID uuid, Role role);
//}