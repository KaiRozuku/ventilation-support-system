package com.ipze.dto.request;

public record UpdateUserRequest(
        String firstNameUKR,
        String lastNameUKR,
        String phoneNumber
) {}