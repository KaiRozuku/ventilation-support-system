package com.ipze.controller;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth/api/account")
@RestController
@RequiredArgsConstructor
public class UserControllerAuthService {

    private final UserAccountService userAccountService;
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request) {

        userAccountService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestBody UpdateUserRequest request) {

        userAccountService.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/email")
    public ResponseEntity<Void> changeEmail(
            @RequestBody ChangeEmailRequest request) {

        userAccountService.changeEmail(request);
        return ResponseEntity.noContent().build();
    }
}