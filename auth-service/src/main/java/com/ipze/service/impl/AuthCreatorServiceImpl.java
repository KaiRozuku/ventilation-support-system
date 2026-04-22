package com.ipze.service.impl;

import com.ipze.exception.UserNotFoundException;
import com.ipze.model.postgres.Role;
import com.ipze.model.postgres.User;
import com.ipze.repository.UserRepository;
import com.ipze.service.AuthCreatorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthCreatorServiceImpl implements AuthCreatorService {

    private final UserRepository userRepository;

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository
                .findAll(pageable);
    }

    @Override
    public Page<User> getUsersByRole(Role role, Pageable pageable) {
        return userRepository
                .findAllByRole(role, pageable);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository
                        .findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void changeRoleOfUser(UUID uuid, Role role) {
        userRepository
                .findById(uuid)
                .orElseThrow(UserNotFoundException::new)
                .setRole(role);
    }

    @Override
    public boolean existByUuid (String uuid) {
        return userRepository.existsByUserID(UUID.fromString(uuid));
    }
}