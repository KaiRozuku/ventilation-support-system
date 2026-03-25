package com.ipze.controller;

import com.ipze.dto.request.LoginRequest;
import com.ipze.dto.request.RegisterRequest;
import com.ipze.dto.response.AuthResponse;
import com.ipze.model.postgres.Role;
import com.ipze.security.JwtService;
import com.ipze.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request, Role.UNDEFINED);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var user = authService.login(request);

        String accessToken =
                jwtService.generateAccessToken(
                        user.email(),
                        user.userID(),
                        user.role() != null ? user.role() : Role.UNDEFINED);

        String refreshToken =
                jwtService.generateRefreshToken(user.email(), user.userID());

//        tokenRedisService.saveRefreshToken(user.username(), refreshToken);

        return ResponseEntity.ok(new AuthResponse(
                        user.username(),
                        accessToken,
                        refreshToken
                )
        );
    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestParam String accessToken) {
//        long expiryMillis = jwtService.extractExpiration(accessToken).getTime() - System.currentTimeMillis();
//        tokenRedisService.revokeAccessToken(accessToken, expiryMillis);
//        return ResponseEntity.ok("Logged out successfully");
//    }
}