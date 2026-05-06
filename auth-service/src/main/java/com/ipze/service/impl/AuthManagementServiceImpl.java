package com.ipze.service.impl;

import com.ipze.controller.UserShortDto;
import com.ipze.exception.UserNotFoundException;
import com.ipze.model.postgres.Role;
import com.ipze.model.postgres.User;
import com.ipze.repository.UserRepository;
import com.ipze.service.AuthManagementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SYSTEM', 'ADMIN')")
public class AuthManagementServiceImpl implements AuthManagementService {

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
    @PreAuthorize("hasAuthority('SYSTEM')")
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

    @Override
    @PreAuthorize("hasAuthority('SYSTEM')")
    public List<UserShortDto> getUsersShort(List<UUID> ids) {
        return userRepository.findAllById(ids)
                .stream()
                .map(user -> new UserShortDto(
                        user.getUserID(),
                        user.getUsername(),
                        user.getEmail(),
                        null
                ))
                .toList();
    }
}