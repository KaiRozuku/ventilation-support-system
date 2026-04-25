package com.ipze.service.interfaces;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;

import com.ipze.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

import reactor.core.publisher.Mono;

/**
 * Основні методи для SystemUser
 * @author vasylnosal
 */
public interface SystemService {

    Mono<PageResponse<UserDto>> getAllUsers(Pageable pageable);

    Mono<PageResponse<UserDto>> getUsersByRole(Role role, Pageable pageable);

    Mono<UserDto> getUserByEmail(String email);

    Mono<Void> changeRoleOfUser(UUID uuid, Role role);
}