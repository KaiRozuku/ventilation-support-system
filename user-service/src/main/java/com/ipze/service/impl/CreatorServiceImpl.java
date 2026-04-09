package com.ipze.service.impl;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;
import com.ipze.service.interfaces.CreatorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Реалізація інтерфейсу {@link CreatorService}
 */

@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {

    private final WebClientUtils webClientUtils;

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable, HttpServletRequest request) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<UserDto>>() {},
                String.format("/auth-service/api/creator/users?page=%d&size=%d",
                        pageable.getPageNumber(), pageable.getPageSize()),
                request
        ).block();
    }

    @Override
    public Page<UserDto> getUsersByRole(Role role, Pageable pageable, HttpServletRequest request) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<UserDto>>() {},
                String.format("/auth-service/api/creator/users?page=%d&size=%d&role=%s",
                        pageable.getPageNumber(), pageable.getPageSize(), role.name()),
                request
        ).block();
    }

    @Override
    public UserDto getUserByEmail(String email, HttpServletRequest request) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<UserDto>() {},
                String.format("/auth-service/api/creator/users?email=%s",  email),
                request
        ).block();
    }

    @Override
    public void changeRoleOfUser(UUID id, Role role, HttpServletRequest request) {
        webClientUtils.sendPutRequest(
                String.format("/auth-service/api/creator/users/%s?role=%s", id, role.name()),
                request
        ).block();
    }
}