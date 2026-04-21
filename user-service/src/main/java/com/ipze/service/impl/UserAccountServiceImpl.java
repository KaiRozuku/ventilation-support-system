package com.ipze.service.impl;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.service.interfaces.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final WebClientUtils webClientUtils;

    @Override
    public Mono<Void> changePassword(ChangePasswordRequest request) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/api/account/password")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<Void> updateUser(UpdateUserRequest request) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/api/account")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<Void> changeEmail(ChangeEmailRequest request) {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/api/account/email")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<>() {}
        );
    }

    @Override
    public Mono<Void> logout() {
        return webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/auth/logout")
                        .toUriString()
        );
    }
}