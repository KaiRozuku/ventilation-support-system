package com.ipze.dto.request;


import com.ipze.model.postgres.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(
        UUID userID,
        String firstNameUKR,
        String lastNameUKR,
        String username,
        String email,
        String phoneNumber,
        Boolean active,
        Role role,
        LocalDateTime createdAt
) {}