package com.ipze.controller;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;
import com.ipze.dto.response.PageResponse;
import com.ipze.service.interfaces.SystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/user/system")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SYSTEM')")
public class SystemController {

    private final SystemService systemService;

    // GET /users?role=ROLE&email=email&page=0&size=10
    @GetMapping("/users")
    public Mono<ResponseEntity<PageResponse<UserDto>>> getUsers(
            @RequestParam(value = "role", required = false) Role role,
            @RequestParam(value = "email", required = false) String email,
            @PageableDefault Pageable pageable
    ) {
        return (
                (email != null) ? systemService.getUserByEmail(email)
                        : (role != null) ? systemService.getUsersByRole(role, pageable)
                        : systemService.getAllUsers(pageable)
        )
                .thenReturn(ResponseEntity.ok().build());
    }

    @PutMapping("/users/{id}")
    public Mono<ResponseEntity<Void>> changeUserRole(
            @PathVariable UUID id,
            @RequestParam("role") Role role
    ) {
        return (systemService.changeRoleOfUser(id, role))
                .thenReturn(ResponseEntity.ok().build());
    }
}