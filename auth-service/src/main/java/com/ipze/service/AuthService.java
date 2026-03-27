package com.ipze.service;

import com.ipze.dto.request.LoginRequest;
import com.ipze.dto.request.RegisterRequest;
import com.ipze.dto.request.UserDto;
import com.ipze.model.postgres.Role;

public interface AuthService {

    void register(RegisterRequest request, Role role);

    UserDto login(LoginRequest request);

    interface AuthCreatorService {
    }
}