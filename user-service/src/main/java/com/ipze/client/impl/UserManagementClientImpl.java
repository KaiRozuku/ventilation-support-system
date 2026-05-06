package com.ipze.client.impl;

import com.ipze.client.interfaces.UserManagementClient;
import com.ipze.dto.Role;
import com.ipze.dto.UserDto;
import com.ipze.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SYSTEM','ADMIN')")
public class UserManagementClientImpl implements UserManagementClient {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<PageResponse<UserDto>> getAllUsers(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("/auth/api/system/users?page={page}&size={size}")
                        .buildAndExpand(pageable.getPageNumber(), pageable.getPageSize())
                                .toUriString(),
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<PageResponse<UserDto>> getUsersByRole(Role role, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("/auth/api/system/users?page={page}&size={size}&role={role}")
                        .buildAndExpand(pageable.getPageNumber(), pageable.getPageSize(), role.name())
                        .toUriString(),
                new ParameterizedTypeReference<>() {}
                );
    }

    @Override
    public Mono<UserDto> getUserByEmail(String email) {
        return webClientUtils.sendGetRequest(
                UriComponentsBuilder
                        .fromPath("/auth/api/system/users?email={email}")
                        .buildAndExpand(email)
                        .toUriString(),
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    @PreAuthorize("hasAuthority('SYSTEM')")
    public Mono<Void> changeRoleOfUser(UUID id, Role role) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth/api/system/users/{id}?role={role}")
                        .buildAndExpand(id, role.name())
                        .toUriString()
        );
    }
}