package com.ipze.controller;

import com.ipze.dto.request.LoginRequest;
import com.ipze.dto.request.RefreshTokenRequestDto;
import com.ipze.dto.request.RegisterRequest;
import com.ipze.dto.response.AuthResponse;
import com.ipze.dto.response.JwtResponse;
import com.ipze.mapper.UserMapper;
import com.ipze.model.postgres.Role;
import com.ipze.security.JwtService;
import com.ipze.service.AuthService;
import com.ipze.service.redis.RefreshTokenService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        var user = userMapper.toDto(authService.login(request));

        String accessToken =
                jwtService.generateAccessToken(
                        user.email(),
                        user.userID(),
                        user.role() != null ? user.role() : Role.UNDEFINED);

        String refreshToken =
                jwtService.generateRefreshToken(user.email(), user.userID());

        refreshTokenService.writeRefreshToken(user.email(), refreshToken);
        log.info("Write to Redis {} user id {}", refreshToken, user.userID());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(new AuthResponse(
                        user.username(),
                        accessToken,
                        refreshToken
                )
        );
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(
            @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {

        if (refreshTokenService.isBlacklisted(jwtService.extractJti(refreshTokenRequestDto.token())))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var user = refreshTokenService.getUserFromRefreshToken(refreshTokenRequestDto.token());
        log.info("user:");
        String accessToken = jwtService.generateAccessToken(
                user.email(), user.userID(), user.role() != null ? user.role() : Role.UNDEFINED
        );
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body(
                        JwtResponse
                                .builder()
                                .accessToken(accessToken)
                                .token(refreshTokenRequestDto.token())
                                .build()
                );
    }

    @PutMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.substring(7);

        refreshTokenService.blacklist(
                jwtService.extractJti(token),
                jwtService.extractExpiration(token)
        );
        return ResponseEntity.ok("Logout successful");
    }
}