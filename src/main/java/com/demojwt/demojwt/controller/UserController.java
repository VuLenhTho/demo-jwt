package com.demojwt.demojwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/manager")
public class UserController {
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> userAccess() {
        return ResponseEntity.status(200).body("Success User");
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('PM') or hasRole('USER')")
    public ResponseEntity<?> projectManagementAccess() {
        return ResponseEntity.status(200).body("Success User");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return ">>> Admin Contents";
    }

}
