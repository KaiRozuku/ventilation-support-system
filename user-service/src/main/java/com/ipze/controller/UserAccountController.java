package com.ipze.controller;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangeNameRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.service.interfaces.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/account")
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        userAccountService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/name")
    public ResponseEntity<Void> changeName(@RequestBody ChangeNameRequest request) {
        userAccountService.changeName(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/email")
    public ResponseEntity<Void> changeEmail(@RequestBody ChangeEmailRequest request) {
        userAccountService.changeEmail(request);
        return ResponseEntity.noContent().build();
    }
}