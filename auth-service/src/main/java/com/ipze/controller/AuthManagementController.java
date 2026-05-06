package com.ipze.controller;

import com.ipze.mapper.UserMapper;
import com.ipze.model.postgres.Role;
import com.ipze.service.AuthManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/management")
@RequiredArgsConstructor

public class AuthManagementController {

    private final AuthManagementService authManagementService;
    private final UserMapper userMapper;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@PageableDefault final Pageable pageable,
                                         @RequestParam(value = "role", required = false) Role role,
                                         @RequestParam(value = "email", required = false) String email
                                                     ) {
        return ResponseEntity.ok(
                (role != null) ? authManagementService.getUsersByRole(role,pageable).map(userMapper::toDto) :
                (email != null) ? userMapper.toDto(authManagementService.getUserByEmail(email)) :
                authManagementService.getAllUsers(pageable).map(userMapper::toDto)
        );
    }

    @PostMapping("/users/batch")
    public ResponseEntity<List<UserShortDto>> getUsers(@RequestBody List<UUID> ids) {
        return ResponseEntity.ok(authManagementService.getUsersShort(ids));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> changeUserRole(@PathVariable("id") UUID id, @RequestParam("role") Role role) {
        authManagementService.changeRoleOfUser(id, role);
        return ResponseEntity.ok("Role updated successfully");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Void> existByUuid(@PathVariable String id) {
        return authManagementService.existByUuid(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}