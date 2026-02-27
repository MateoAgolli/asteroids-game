package com.asteroids.backend.controller;

import com.asteroids.backend.dto.AuthRequest;
import com.asteroids.backend.entity.User;
import com.asteroids.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@RequestBody AuthRequest request) {
        authService.register(request);
    }

    @PostMapping("/login")
    public User login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
