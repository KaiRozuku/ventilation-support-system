package com.ipze.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "Ім'я обов'язкове")
        String firstNameUKR,

        @NotBlank(message = "Прізвище обов'язкове")
        String lastNameUKR,

        @NotBlank(message = "Username обов'язковий")
        @Size(min = 4, max = 20, message = "Username має бути 4-20 символів")
        String username,

        @NotBlank(message = "Email обов'язковий")
        @Email(message = "Некоректний email")
        String email,

        @NotBlank(message = "Телефон обов'язковий")
        @Pattern(
                regexp = "^\\+380\\d{9}$",
                message = "Номер має бути у форматі +380XXXXXXXXX"
        )
        String phoneNumber,

        @NotBlank(message = "Пароль обов'язковий")
        @Size(min = 6, message = "Пароль має бути мінімум 6 символів")
        String password

) {}