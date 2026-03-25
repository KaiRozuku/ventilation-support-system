//
//package com.ipze.controller;
//
//import com.ipze.dto.request.ChangeEmailRequest;
//import com.ipze.dto.request.ChangePasswordRequest;
//import com.ipze.dto.request.UpdateUserRequest;
//import com.ipze.service.interfaces.UserAccountService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/account")
//public class UserAccountController {
//
//    private final UserAccountService userAccountService;
//
//    @PutMapping("/password")
//    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request,
//                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//        userAccountService.changePassword(request, token);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PreAuthorize("hasAnyAuthority('CREATOR','ADMIN','UNDEFINED')")
//    @PutMapping("/name")
//    public ResponseEntity<Void> changeUser(@RequestBody UpdateUserRequest request,
//                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//        userAccountService.updateUser(request, token);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PreAuthorize("hasAnyAuthority('CREATOR','ADMIN','UNDEFINED')")
//    @PutMapping("/email")
//    public ResponseEntity<Void> changeEmail(@RequestBody ChangeEmailRequest request,
//                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//        userAccountService.changeEmail(request, token);
//        return ResponseEntity.noContent().build();
//    }
//}
package com.ipze.controller;

import com.ipze.dto.request.ChangeEmailRequest;
import com.ipze.dto.request.ChangePasswordRequest;
import com.ipze.dto.request.UpdateUserRequest;
import com.ipze.service.interfaces.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/account")  // <--- підлаштовано під Gateway rewrite
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PreAuthorize("hasAnyAuthority('CREATOR','ADMIN','UNDEFINED')")
    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        userAccountService.changePassword(request, token);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('CREATOR','ADMIN','UNDEFINED')")
    @PutMapping("/name")
    public ResponseEntity<Void> changeUser(@RequestBody UpdateUserRequest request,
                                           @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        userAccountService.updateUser(request, token);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('CREATOR','ADMIN','UNDEFINED')")
    @PutMapping("/email")
    public ResponseEntity<Void> changeEmail(@RequestBody ChangeEmailRequest request,
                                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        userAccountService.changeEmail(request, token);
        return ResponseEntity.noContent().build();
    }
}