package com.ipze.dto.request;

import java.util.UUID;

public record ChangePasswordRequest(
        UUID userUuid,
        String oldPassword,
        String newPassword
) {}