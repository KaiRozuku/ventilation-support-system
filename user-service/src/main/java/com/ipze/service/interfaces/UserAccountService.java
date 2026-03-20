package com.ipze.service.interfaces;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangeNameRequest;
import com.ipze.dto.request.ChangePasswordRequest;

public interface UserAccountService {

    void changePassword(ChangePasswordRequest request);

    void changeName(ChangeNameRequest request);

    void changeEmail(ChangeEmailRequest request);

}