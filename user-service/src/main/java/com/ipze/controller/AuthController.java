///package com.ipze.controller;
//
//import com.ipze.dto1.request.LoginRequest;
//import com.ipze.dto1.request.LogoutRequest;
//import com.ipze.dto1.request.RegisterRequest;
//import com.ipze.dto1.response.AuthResponse;
//import com.ipze.dto1.response.JwtResponse;
//import com.ipze.dto1.response.MessageResponse;
//import io.jsonwebtoken.ExpiredJwtException;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import com.ipze.service.interfaces.AuthService;
//
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//
//public class AuthController {
//
//    private final AuthService authService;
//    private final UserDetailsService userDetailsService;
//
//    @PostMapping("/logout")
//    public ResponseEntity<MessageResponse> logout(@RequestBody LogoutRequest request) {
//        return ResponseEntity.ok(authService.logout(request));
//    }
////    @PostMapping("/refresh")
////    public ResponseEntity<AuthResponse> refreshToken(@RequestBody Map<String, String> request) {
////        String refreshToken = request.get("refreshToken");
////        if (refreshToken == null || refreshToken.isEmpty()) {
////            return ResponseEntity.badRequest().build();
////        }
////        String username;
////        try {
////            username = jwtService.extractUsername(refreshToken);
////        } catch (ExpiredJwtException e) {
////            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).build();
////        }
////
////        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
////
////        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
////            return ResponseEntity
////                    .status(HttpServletResponse.SC_UNAUTHORIZED)
////                    .build();
////        }
////
////        String newAccessToken = jwtService.generateToken(userDetails, TimeUnit.MINUTES.toMillis(15));
////        return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken));
////    }
//}