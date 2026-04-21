package com.ipze.service.impl;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;
import com.ipze.dto.response.PageResponse;
import com.ipze.service.interfaces.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Реалізація інтерфейсу {@link CreatorService}
 */

@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<PageResponse<UserDto>> getAllUsers(Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/auth-service/api/creator/users?page={page}&size={size}")
                        .buildAndExpand(pageable.getPageNumber(), pageable.getPageSize())
                                .toUriString()
        );
    }

    @Override
    public Mono<PageResponse<UserDto>> getUsersByRole(Role role, Pageable pageable) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/auth-service/api/creator/users?page={page}&size={size}&role={role}")
                        .buildAndExpand(pageable.getPageNumber(), pageable.getPageSize(), role.name())
                        .toUriString()
        );
    }

    @Override
    public Mono<UserDto> getUserByEmail(String email) {
        return webClientUtils.sendGetRequest(
                new ParameterizedTypeReference<>() {},
                UriComponentsBuilder
                        .fromPath("/auth-service/api/creator/users?email={email}")
                        .buildAndExpand(email)
                        .toUriString()
        );
    }

    @Override
    public Mono<Void> changeRoleOfUser(UUID id, Role role) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/api/creator/users/{id}?role={role}")
                        .buildAndExpand(id, role.name())
                        .toUriString()
        );
    }
}