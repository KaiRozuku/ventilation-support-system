package com.ipze.service;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserAccountService {

    void changePassword(ChangePasswordRequest request, HttpServletRequest httpServletRequest);

    void updateUser(UpdateUserRequest request, HttpServletRequest httpServletRequest);

    void changeEmail(ChangeEmailRequest request, HttpServletRequest httpServletRequest);
}