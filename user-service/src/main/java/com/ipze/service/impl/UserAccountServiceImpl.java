package com.ipze.service.impl;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.service.interfaces.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final WebClientUtils webClientUtils;

    @Override
    public void changePassword(ChangePasswordRequest request) {
        webClientUtils.sendPutRequest(
                "/auth-service/api/account/password",
                request,
                new ParameterizedTypeReference<ChangePasswordRequest>() {}
        ).block();

    }

    @Override
    public void updateUser(UpdateUserRequest request) {
        webClientUtils.sendPutRequest(
                "/auth-service/api/account",
                request,
                new ParameterizedTypeReference<Void>() {}
        ).block();
    }

    @Override
    public void changeEmail(ChangeEmailRequest request) {
        webClientUtils.sendPutRequest(
                "/auth-service/api/account/email",
                request,
                new ParameterizedTypeReference<Void>() {}
        ).block();
    }

    @Override
    public void logout() {
        webClientUtils.sendPutRequest(
                "/auth-service/auth/logout"
        ).block();
    }
}