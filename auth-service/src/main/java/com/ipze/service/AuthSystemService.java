package com.ipze.service;

import com.ipze.model.postgres.Role;
import com.ipze.model.postgres.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AuthSystemService {

    Page<User> getAllUsers(Pageable pageable);

    Page<User> getUsersByRole(Role role, Pageable pageable);

    User getUserByEmail(String email);

    void changeRoleOfUser(UUID uuid, Role role);

    boolean existByUuid(String uuid);

}