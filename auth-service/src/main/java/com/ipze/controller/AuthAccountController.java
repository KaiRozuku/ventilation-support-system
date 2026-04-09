package com.ipze.controller;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth/api/account")
@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class AuthAccountController {

    private final UserAccountService userAccountService;
    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request,
            @NonNull HttpServletRequest httpServletRequest) {
        userAccountService.changePassword(request, httpServletRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestBody UpdateUserRequest request,
            @NonNull HttpServletRequest httpServletRequest) {

        userAccountService.updateUser(request, httpServletRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/email")
    public ResponseEntity<Void> changeEmail(
            @RequestBody ChangeEmailRequest request,
            @NonNull HttpServletRequest httpServletRequest) {

        userAccountService.changeEmail(request, httpServletRequest);
        return ResponseEntity.noContent().build();
    }
}