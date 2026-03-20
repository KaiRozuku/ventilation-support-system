package com.ipze.service.impl;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangeNameRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.service.interfaces.UserAccountService;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Override
    public void changePassword(ChangePasswordRequest request){}


    @Override
    public void changeName(ChangeNameRequest request){}

    @Override
    public void changeEmail(ChangeEmailRequest request){}
//
//
//    @Override
//    @Transactional
//    public void changePassword(ChangePasswordRequest request) {
//        User user = userRepository.findById(request.userUuid())
//                .orElseThrow(UserNotFoundException::new);
//
//        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
//            throw new InvalidPasswordOrEmailException("Invalid old password");
//        }
//
//        user.setPassword(passwordEncoder.encode(request.newPassword()));
//        userRepository.save(user);
//    }
//
//    @Transactional
//    public void changeName(ChangeNameRequest request) {
//        User user = userRepository.findById(request.uuid())
//                .orElseThrow(UserNotFoundException::new);
//        if (request.name() == null || request.name().isBlank()) {
//            throw new IllegalArgumentException("Name cannot be null or empty");
//        }
//        user.setNameUKR(request.name());
//        userRepository.save(user);
//    }
//
//    @Transactional
//    public void changeEmail(ChangeEmailRequest request) {
//        User user = userRepository.findById(request.uuid())
//                .orElseThrow(UserNotFoundException::new);
//        if (request.newEmail() == null || request.newEmail().isBlank()) {
//            throw new IllegalArgumentException("Email cannot be null or empty");
//        }
//        user.setEmail(request.newEmail());
//        userRepository.save(user);
//    }
}