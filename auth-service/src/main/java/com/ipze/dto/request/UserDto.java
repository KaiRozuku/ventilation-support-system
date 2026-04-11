package com.ipze.dto.request;

import com.ipze.model.postgres.Role;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDto(

        @NotNull(message = "User ID cannot be null")
        UUID userID,

        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "First name must be less than 50 characters")
        String firstNameUKR,

        @NotBlank(message = "Last name cannot be blank")
        @Size(max = 50, message = "Last name must be less than 50 characters")
        String lastNameUKR,

        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @Pattern(
                regexp = "^\\+?[0-9]{10,15}$",
                message = "Phone number must be valid"
        )
        String phoneNumber,

        @NotNull(message = "Active flag cannot be null")
        Boolean active,

        @NotNull(message = "Role cannot be null")
        Role role,

        @PastOrPresent(message = "Created date cannot be in the future")
        LocalDateTime createdAt
) {}