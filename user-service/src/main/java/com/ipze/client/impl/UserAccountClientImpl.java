package com.ipze.client.impl;

import com.ipze.client.interfaces.UserAccountClient;
import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserAccountClientImpl implements UserAccountClient {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<Void> changePassword(ChangePasswordRequest request) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth/api/account/password")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<Void> updateUser(UpdateUserRequest request) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth/api/account")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<Void> changeEmail(ChangeEmailRequest request) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth/api/account/email")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<Void> logout() {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth/auth/logout")
                        .toUriString()
        );
    }
}