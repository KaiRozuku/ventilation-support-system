package com.ipze.service;

import com.ipze.dto.request.UserDto;
import com.ipze.model.postgres.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AuthCreatorService {

    Page<UserDto> getAllUsers(Pageable pageable);

    Page<UserDto> getUsersByRole(Role role, Pageable pageable);

    UserDto getUserByEmail(String email);

    void changeRoleOfUser(UUID uuid, Role role);
}