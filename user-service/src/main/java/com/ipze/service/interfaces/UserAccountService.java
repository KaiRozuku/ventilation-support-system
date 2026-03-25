package com.ipze.service.interfaces;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;

public interface UserAccountService {

    void changePassword(ChangePasswordRequest request, String token);

    void updateUser(UpdateUserRequest request, String token);

    void changeEmail(ChangeEmailRequest request, String token);
}