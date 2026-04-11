package com.ipze.service.impl;

import com.ipze.dto.request.LoginRequest;
import com.ipze.dto.request.RegisterRequest;
import com.ipze.exception.EmailAlreadyExistException;
import com.ipze.exception.InvalidPasswordOrEmailException;
import com.ipze.exception.UserNotFoundException;
import com.ipze.repository.UserRepository;
import com.ipze.model.postgres.Role;
import com.ipze.model.postgres.User;
import com.ipze.service.AuthService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) throw new EmailAlreadyExistException();

        userRepository.save(
                User.builder()
                        .firstNameUKR(request.firstNameUKR())
                        .lastNameUKR(request.lastNameUKR())
                        .username(request.username())
                        .email(request.email())
                        .phoneNumber(request.phoneNumber())
                        .password(passwordEncoder.encode(request.password()))
                        .role(Role.UNDEFINED)
                        .active(true)
                        .build()
        );
    }

    @Override
    public User login(LoginRequest request) {
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        if (!user.getEmail().equals(request.email())
                || !passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidPasswordOrEmailException();
        }

        return user;
    }
}