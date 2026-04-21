package com.ipze.controller;

import com.ipze.dto.Role;
import com.ipze.dto.UserDto;
import com.ipze.dto.response.PageResponse;
import com.ipze.service.interfaces.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/user/creator")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATOR')")
public class CreatorController {

    private final CreatorService creatorService;

    // GET /users?role=ROLE&email=email&page=0&size=10
    @GetMapping("/users")
    public Mono<ResponseEntity<PageResponse<UserDto>>> getUsers(
            @RequestParam(value = "role", required = false) Role role,
            @RequestParam(value = "email", required = false) String email,
            @PageableDefault Pageable pageable
    ) {
        return (
                (email != null) ? creatorService.getUserByEmail(email)
                        : (role != null) ? creatorService.getUsersByRole(role, pageable)
                        : creatorService.getAllUsers(pageable)
        )
                .thenReturn(ResponseEntity.ok().build());
    }

    @PutMapping("/users/{id}")
    public Mono<ResponseEntity<Void>> changeUserRole(
            @PathVariable UUID id,
            @RequestParam("role") Role role
    ) {
        return (creatorService.changeRoleOfUser(id, role))
                .thenReturn(ResponseEntity.ok().build());
    }
}