package com.ipze.controller;

import com.ipze.mapper.UserMapper;
import com.ipze.model.postgres.Role;
import com.ipze.service.AuthSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth/api/system")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SYSTEM')")

public class AuthSystemController {

    private final AuthSystemService authSystemService;
    private final UserMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@PageableDefault final Pageable pageable,
                                         @RequestParam(value = "role", required = false) Role role,
                                         @RequestParam(value = "email", required = false) String email
                                                     ) {
        return ResponseEntity.ok(
                (role != null) ? authSystemService.getUsersByRole(role,pageable).map(userMapper::toDto) :
                (email != null) ? userMapper.toDto(authSystemService.getUserByEmail(email)) :
                authSystemService.getAllUsers(pageable).map(userMapper::toDto)
        );
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> changeUserRole(@PathVariable("id") UUID id, @RequestParam("role") Role role) {
        authSystemService.changeRoleOfUser(id, role);
        return ResponseEntity.ok("Role updated successfully");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Void> existByUuid(@PathVariable String id) {
        boolean exists = authSystemService.existByUuid(id);

        return exists
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}