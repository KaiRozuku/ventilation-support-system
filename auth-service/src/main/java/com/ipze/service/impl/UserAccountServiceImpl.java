package com.ipze.service.impl;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.exception.InvalidEmailException;
import com.ipze.exception.InvalidPasswordOrEmailException;
import com.ipze.exception.UserNotFoundException;
import com.ipze.model.postgres.User;
import com.ipze.repository.UserRepository;
import com.ipze.service.UserAccountService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private boolean hasValue(String string) {
        return !ObjectUtils.isEmpty(string);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request, UUID userUuid) {

        User user = userRepository.findById(userUuid)
                .orElseThrow(UserNotFoundException::new);

        if (!hasValue(request.oldPassword())
                || !passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new InvalidPasswordOrEmailException();
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserRequest request, UUID userUuid) {
        User user = userRepository.findById(userUuid).orElseThrow(UserNotFoundException::new);

        if (hasValue(request.firstNameUKR())) user.setFirstNameUKR(request.firstNameUKR());
        if (hasValue(request.lastNameUKR())) user.setLastNameUKR(request.lastNameUKR());
        if (hasValue(request.phoneNumber())) user.setPhoneNumber(request.phoneNumber());
    }

    @Override
    @Transactional
    public void changeEmail(ChangeEmailRequest request, UUID userUuid) {
        User user = userRepository.findById(userUuid).orElseThrow(UserNotFoundException::new);

        if (!hasValue(request.newEmail())) {
            throw new InvalidEmailException("Email cannot be null or empty");
        }

        user.setEmail(request.newEmail());
    }
}