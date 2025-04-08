package org.project.musicweb.controller;

import org.project.musicweb.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Admin Dashboard");
    }

}
