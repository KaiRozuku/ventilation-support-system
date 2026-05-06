package com.ipze.controller;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.security.ApplicationUserDetails;
import com.ipze.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/account")
@RestController
@RequiredArgsConstructor
public class AuthAccountController {

    private final UserAccountService userAccountService;

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal ApplicationUserDetails user) {
        userAccountService.changePassword(request, user.uuid());
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestBody UpdateUserRequest request,
            @AuthenticationPrincipal ApplicationUserDetails user) {

        userAccountService.updateUser(request, user.uuid());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/email")
    public ResponseEntity<Void> changeEmail(
            @RequestBody ChangeEmailRequest request,
            @AuthenticationPrincipal ApplicationUserDetails user) {

        userAccountService.changeEmail(request, user.uuid());
        return ResponseEntity.noContent().build();
    }
}