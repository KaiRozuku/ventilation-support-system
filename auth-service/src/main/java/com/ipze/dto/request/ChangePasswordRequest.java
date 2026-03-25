package com.ipze.dto.request;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {}