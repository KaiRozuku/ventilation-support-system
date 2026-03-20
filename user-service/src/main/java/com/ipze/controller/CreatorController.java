//package com.ipze.controller;
//
//import com.ipze.dto1.UserDto;
//import com.ipze.dto1.response.MessageResponse;
//import com.ipze.mapper.UserMapper;
//import com.ipze.service.impl.CreatorServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/creator")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*") // ?
//@PreAuthorize("hasAuthority('CREATOR')")
//
//public class CreatorController {
//
//    private final CreatorServiceImpl creatorService;
//    private final UserMapper userMapper;
//
//    @GetMapping("/users")
//    public ResponseEntity<Page<UserDto>> getAllUsers(@PageableDefault final Pageable pageable) {
//        return ResponseEntity.ok(
//                creatorService(pageable)
//                        .map(userMapper::fromUserToDto)
//        );
//    }
//
//    @GetMapping("/users/by-role")
//    public ResponseEntity<Page<UserDto>> getUsersByRole(@RequestParam("role") Role role, @PageableDefault final Pageable pageable) {
//        return ResponseEntity.ok(
//                creatorService.getUsersByRole(role, pageable)
//                        .map(userMapper::fromUserToDto)
//        );
//    }
//
//    @GetMapping("/users/by-email")
//    public ResponseEntity<UserDto> getUserByEmail(@RequestParam("email") String email) {
//        return ResponseEntity.ok(
//                userMapper.fromUserToDto(creatorService.getUserByEmail(email))
//        );
//    }
//
//    @PutMapping("/users/{id}/role")
//    public ResponseEntity<MessageResponse> changeUserRole(
//            @PathVariable("id") UUID id,
//            @RequestParam("role") Role role) {
//        creatorService.changeRoleOfUser(id, role);
//        return ResponseEntity.ok(new MessageResponse("Role updated successfully"));
//    }
//}