package com.ipze.service.impl;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.exception.InvalidEmailException;
import com.ipze.exception.InvalidPasswordOrEmailException;
import com.ipze.exception.UserNotFoundException;
import com.ipze.model.postgres.User;
import com.ipze.repository.UserRepository;
import com.ipze.security.ApplicationUserDetails;
import com.ipze.service.UserAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() == null) {
            throw new RuntimeException("User is not authenticated");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof ApplicationUserDetails userDetails) {
            return userDetails.uuid();
        } else if (principal instanceof String username) {
            log.info("username {}", username);
            User user = userRepository.findUserByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found for principal"));

            return user.getUserID().toString();
        } else {
            throw new RuntimeException("Principal is not valid: " + principal.getClass());
        }
    }

    private boolean hasValue(String string) {
        return !ObjectUtils.isEmpty(string);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        String id = getCurrentUserId();

        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(UserNotFoundException::new);

        if (!hasValue(request.oldPassword())
                || !passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new InvalidPasswordOrEmailException();
        }
        System.out.println(request.newPassword() + "\n" + request.oldPassword());
        user.setPassword(passwordEncoder.encode(request.newPassword()));
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserRequest request) {
        String id = getCurrentUserId();
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(UserNotFoundException::new);

        if (hasValue(request.firstNameUKR())) user.setFirstNameUKR(request.firstNameUKR());
        if (hasValue(request.lastNameUKR())) user.setLastNameUKR(request.lastNameUKR());
        if (hasValue(request.phoneNumber())) user.setPhoneNumber(request.phoneNumber());
    }

    @Override
    @Transactional
    public void changeEmail(ChangeEmailRequest request) {
        String id = getCurrentUserId();
        User user = userRepository.findById(UUID.fromString(id)).orElseThrow(UserNotFoundException::new);

        if (!hasValue(request.newEmail())) {
            throw new InvalidEmailException("Email cannot be null or empty");
        }

        user.setEmail(request.newEmail());
    }
}