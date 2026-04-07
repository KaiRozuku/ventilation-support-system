package com.ipze.service;

import com.ipze.dto.request.LoginRequest;
import com.ipze.dto.request.RegisterRequest;
import com.ipze.model.postgres.Role;
import com.ipze.model.postgres.User;

public interface AuthService {

    void register(RegisterRequest request, Role role);

    User login(LoginRequest request);
}