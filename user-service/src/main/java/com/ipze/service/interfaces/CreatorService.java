package com.ipze.service.interfaces;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

import org.springframework.data.domain.Page;

/**
 * Основні методи для Creator
 * @author vasylnosal
 */
public interface CreatorService {

    Page<UserDto> getAllUsers(Pageable pageable);

    Page<UserDto> getUsersByRole(Role role, Pageable pageable);

    UserDto getUserByEmail(String email);

    void changeRoleOfUser(UUID uuid, Role role);
}