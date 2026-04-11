package com.ipze.service;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface UserAccountService {

    void changePassword(ChangePasswordRequest request, UUID userUuid);

    void updateUser(UpdateUserRequest request, UUID userUuid);

    void changeEmail(ChangeEmailRequest request, UUID userUuid);
}