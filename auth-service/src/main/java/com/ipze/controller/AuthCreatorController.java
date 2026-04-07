package com.ipze.controller;

import com.ipze.mapper.UserMapper;
import com.ipze.model.postgres.Role;
import com.ipze.service.AuthCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth/api/creator")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CREATOR')")

public class AuthCreatorController {

    private final AuthCreatorService creatorService;
    private final UserMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@PageableDefault final Pageable pageable,
                                         @RequestParam(value = "role", required = false) Role role,
                                         @RequestParam(value = "email", required = false) String email
                                                     ) {
        return ResponseEntity.ok(
                (role != null) ? creatorService.getUsersByRole(role,pageable).map(userMapper::toDto) :
                (email != null) ? userMapper.toDto(creatorService.getUserByEmail(email)) :
                creatorService.getAllUsers(pageable).map(userMapper::toDto)
        );
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> changeUserRole(@PathVariable("id") UUID id, @RequestParam("role") Role role) {
        creatorService.changeRoleOfUser(id, role);
        return ResponseEntity.ok("Role updated successfully");
    }
}