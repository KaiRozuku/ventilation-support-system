package com.ipze.service.impl;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;
import com.ipze.service.interfaces.CreatorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatorServiceImpl implements CreatorService {

    private final WebClient webClient;

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable, HttpServletRequest request) {
        return sendGetRequest(
                new ParameterizedTypeReference<Page<UserDto>>() {},
                "/auth-service/api/creator/users?page=" + pageable.getPageNumber()
                        + "&size=" + pageable.getPageSize(),
                request
        ).block();
    }

    @Override
    public Page<UserDto> getUsersByRole(Role role, Pageable pageable, HttpServletRequest request) {
        return sendGetRequest(
                new ParameterizedTypeReference<Page<UserDto>>() {},
                "/auth-service/api/creator/users?page=" + pageable.getPageNumber()
                        + "&size=" + pageable.getPageSize()
                        + "&role=" + role.name(),
                request
        ).block();
    }

    @Override
    public UserDto getUserByEmail(String email, HttpServletRequest request) {
        return sendGetRequest(
                new ParameterizedTypeReference<UserDto>() {},
                "/auth-service/api/creator/users?email=" + email,
                request
        ).block();
    }

    @Override
    public void changeRoleOfUser(UUID id, Role role, HttpServletRequest request) {
        sendPutRequest(
                "/auth-service/api/creator/users/" + id + "?role=" + role.name(),
                request
        ).block();
    }

    private <T> Mono<T> sendGetRequest(
            @NonNull ParameterizedTypeReference<T> type,
            @NonNull String uri,
            @NonNull HttpServletRequest request
    ) {
        return webClient.get()
                .uri(uri)
                .headers(headers -> copyHeaders(request, headers))
                .retrieve()
                .bodyToMono(type);
    }

    private Mono<Void> sendPutRequest(
            @NonNull String uri,
            @NonNull HttpServletRequest request
    ) {
        return webClient.put()
                .uri(uri)
                .headers(headers -> copyHeaders(request, headers))
                .retrieve()
                .bodyToMono(Void.class);
    }

    private void copyHeaders(HttpServletRequest request, HttpHeaders headers) {
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        String userId = request.getHeader("X-User-ID");
        String username = request.getHeader("X-User-Name");
        String roles = request.getHeader("X-User-Roles");

        if (auth != null) headers.set(HttpHeaders.AUTHORIZATION, auth);
        if (userId != null) headers.set("X-User-ID", userId);
        if (username != null) headers.set("X-User-Name", username);
        if (roles != null) headers.set("X-User-Roles", roles);
    }
}