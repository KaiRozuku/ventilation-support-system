package com.ipze.controller;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.client.interfaces.UserAccountClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account")

public class UserAccountController {

    private final UserAccountClient userAccountClient;

    @PutMapping("/password")
    public Mono<ResponseEntity<Void>> changePassword(@RequestBody ChangePasswordRequest request) {
        return (userAccountClient.changePassword(request))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PutMapping("/name")
    public Mono<ResponseEntity<Void>> changeUser(@RequestBody UpdateUserRequest request) {
        return (userAccountClient.updateUser(request))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PutMapping("/email")
    public Mono<ResponseEntity<Void>> changeEmail(@RequestBody ChangeEmailRequest request) {
        return (userAccountClient.changeEmail(request))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @PutMapping("/logout")
    public Mono<ResponseEntity<Void>> logout(){
        return (userAccountClient.logout())
        .thenReturn(ResponseEntity.noContent().build());
    }
}