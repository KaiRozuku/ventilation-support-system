package com.ipze.service;

import com.ipze.controller.UserShortDto;
import com.ipze.model.postgres.Role;
import com.ipze.model.postgres.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AuthManagementService {

    Page<User> getAllUsers(Pageable pageable);

    Page<User> getUsersByRole(Role role, Pageable pageable);

    User getUserByEmail(String email);

    void changeRoleOfUser(UUID uuid, Role role);

    boolean existByUuid(String uuid);

    List<UserShortDto> getUsersShort(List<UUID> ids);
}