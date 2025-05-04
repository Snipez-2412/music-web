package org.project.musicweb.controller;

import org.project.musicweb.dto.AuthRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login") // Optional - Spring Security handles it now
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        // Spring Security handles login, no need for this endpoint if using formLogin
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Login handled by Spring Security");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO request) {
        // Add registration logic (save the user, encrypt password, etc.)
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username of the logged-in user
        return ResponseEntity.ok("Current user: " + username);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext(); // Clears the authentication context
        return ResponseEntity.ok("Logged out successfully");
    }
}
