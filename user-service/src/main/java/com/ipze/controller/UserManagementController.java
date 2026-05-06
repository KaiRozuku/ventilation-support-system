package com.ipze.controller;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;
import com.ipze.dto.response.PageResponse;
import com.ipze.client.interfaces.UserManagementClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementClient userManagementClient;

    @GetMapping("/users")
    public Mono<ResponseEntity<PageResponse<UserDto>>> getUsers(
            @RequestParam(value = "role", required = false) Role role,
            @RequestParam(value = "email", required = false) String email,
            @PageableDefault Pageable pageable
    ) {
        return (
                (email != null) ? userManagementClient.getUserByEmail(email)
                        : (role != null) ? userManagementClient.getUsersByRole(role, pageable)
                        : userManagementClient.getAllUsers(pageable)
        )
                .thenReturn(ResponseEntity.ok().build());
    }

    @PutMapping("/users/{id}")
    public Mono<ResponseEntity<Void>> changeUserRole(
            @PathVariable UUID id,
            @RequestParam("role") Role role
    ) {
        return (userManagementClient.changeRoleOfUser(id, role))
                .thenReturn(ResponseEntity.ok().build());
    }
}