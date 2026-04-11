package com.ipze.controller;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.service.interfaces.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/account")
@PreAuthorize("isAuthenticated()")

public class UserAccountController {

    private final UserAccountService userAccountService;

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        userAccountService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/name")
    public ResponseEntity<Void> changeUser(@RequestBody UpdateUserRequest request) {
        userAccountService.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/email")
    public ResponseEntity<Void> changeEmail(@RequestBody ChangeEmailRequest request) {
        userAccountService.changeEmail(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(){
        userAccountService.logout();
        return ResponseEntity.noContent().build();
    }
}