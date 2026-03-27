package com.ipze.service.interfaces;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

//package com.ipze.service.interfaces;
////
////import com.ipze.domain.postgres.Role;
////import com.ipze.domain.postgres.User;
import org.springframework.data.domain.Page;
////import org.springframework.data.domain.Pageable;
////
////import java.util.UUID;
////
///**
// * Основні методи для class Creator
// * @author vasylnosal
// */
public interface CreatorService {

    Page<UserDto> getAllUsers(Pageable pageable, HttpServletRequest httpServletRequest);

    Page<UserDto> getUsersByRole(Role role, Pageable pageable, HttpServletRequest httpServletRequest);

    UserDto getUserByEmail(String email, HttpServletRequest httpServletRequest);

    void changeRoleOfUser(UUID uuid, Role role, HttpServletRequest httpServletRequest);
}