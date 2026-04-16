package com.ipze.service.impl;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.service.interfaces.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final WebClientUtils webClientUtils;

    @Override
    public void changePassword(ChangePasswordRequest request) {
        webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/api/account/password")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<ChangePasswordRequest>() {}
        ).block();

    }

    @Override
    public void updateUser(UpdateUserRequest request) {
        webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/api/account")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<Void>() {}
        ).block();
    }

    @Override
    public void changeEmail(ChangeEmailRequest request) {
        webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/api/account/email")
                        .toUriString(),
                request,
                new ParameterizedTypeReference<Void>() {}
        ).block();
    }

    @Override
    public void logout() {
        webClientUtils.sendPutRequest(
                UriComponentsBuilder
                        .fromPath("/auth-service/auth/logout")
                        .toUriString()
        ).block();
    }
}