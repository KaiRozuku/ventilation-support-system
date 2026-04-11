package com.ipze.service.interfaces;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;

public interface UserAccountService {

    void changePassword(ChangePasswordRequest request);

    void updateUser(UpdateUserRequest request);

    void changeEmail(ChangeEmailRequest request);

    void logout();
}