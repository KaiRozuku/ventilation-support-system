package com.ipze.service.impl;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;
import com.ipze.service.interfaces.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

/**
 * Реалізація інтерфейсу {@link CreatorService}
 * remove {@code .block();}
 */

@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {

    private final WebClientUtils webClientUtils;

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<UserDto>>() {},
                UriComponentsBuilder
                        .fromPath("/auth-service/api/creator/users?page={page}&size={size}")
                        .buildAndExpand(pageable.getPageNumber(), pageable.getPageSize())
                                .toUriString()
        ).block();
    }

    @Override
    public Page<UserDto> getUsersByRole(Role role, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<Page<UserDto>>() {},
                UriComponentsBuilder
                        .fromPath("/auth-service/api/creator/users?page={page}&size={size}&role={role}")
                        .buildAndExpand(pageable.getPageNumber(), pageable.getPageSize(), role.name())
                        .toUriString()
        ).block();
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<UserDto>() {},
                UriComponentsBuilder
                        .fromPath("/auth-service/api/creator/users?email={email}")
                        .buildAndExpand(email)
                        .toUriString()
        ).block();
    }

    @Override
    public void changeRoleOfUser(UUID id, Role role) {
        webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/api/creator/users/{id}?role={role}")
                        .buildAndExpand(id, role.name())
                        .toUriString()
        ).block();
    }
}