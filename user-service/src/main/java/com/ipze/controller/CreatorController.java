package com.ipze.controller;

import com.ipze.dto.Role;
import com.ipze.service.interfaces.CreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/creator")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATOR')")
public class CreatorController {

    private final CreatorService creatorService;

    // GET /users?role=ROLE&email=email&page=0&size=10
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestParam(value = "role", required = false) Role role,
            @RequestParam(value = "email", required = false) String email,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(
                (email != null) ? creatorService.getUserByEmail(email)
                        : (role != null) ? creatorService.getUsersByRole(role, pageable)
                        : creatorService.getAllUsers(pageable)
        );
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> changeUserRole(
            @PathVariable UUID id,
            @RequestParam("role") Role role
    ) {
        creatorService.changeRoleOfUser(id, role);
        return ResponseEntity.ok("Role updated successfully");
    }
}