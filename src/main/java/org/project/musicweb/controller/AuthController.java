package org.project.musicweb.controller;

import org.project.musicweb.dto.AuthRequestDTO;
import org.project.musicweb.dto.UserDTO;
import org.project.musicweb.service.UserService;
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
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login") // Optional - Spring Security handles it now
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        // Spring Security handles login, no need for this endpoint if using formLogin
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Login handled by Spring Security");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO request) {
        try {
            UserDTO createdUser = userService.registerNewUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signup failed: " + e.getMessage());
        }
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
