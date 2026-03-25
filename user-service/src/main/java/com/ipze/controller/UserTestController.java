package com.ipze.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserTestController {

    @GetMapping("/info")
    public String userInfo() {
        return "This endpoint is available for all roles (public)";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminInfo() {
        return "This endpoint is available for ADMIN role only";
    }
}