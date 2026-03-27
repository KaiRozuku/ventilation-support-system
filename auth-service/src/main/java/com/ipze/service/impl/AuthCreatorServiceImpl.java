package com.ipze.service.impl;


import com.ipze.dto.request.UserDto;
import com.ipze.exception.UserNotFoundException;
import com.ipze.mapper.UserMapper;
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
    private final UserMapper userMapper;

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository
                .findAll(pageable)
                .map(userMapper::toDto);
    }

    @Override
    public Page<UserDto> getUsersByRole(Role role, Pageable pageable) {
        return userRepository
                .findAllByRole(role, pageable)
                .map(userMapper::toDto);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return userMapper.toDto(
                userRepository
                        .findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new)
        );
    }

    @Transactional
    public void changeRoleOfUser(UUID uuid, Role role) {
        User user = userRepository
                .findById(uuid)
                .orElseThrow(UserNotFoundException::new);
        user.setRole(role);
    }
}