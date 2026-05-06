package com.ipze.client.interfaces;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;

import com.ipze.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

import reactor.core.publisher.Mono;

/**
 * Основні методи для управління користувачами
 * @author vasylnosal
 */
public interface UserManagementClient {

    Mono<PageResponse<UserDto>> getAllUsers(Pageable pageable);

    Mono<PageResponse<UserDto>> getUsersByRole(Role role, Pageable pageable);

    Mono<UserDto> getUserByEmail(String email);

    Mono<Void> changeRoleOfUser(UUID uuid, Role role);
}