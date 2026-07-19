package com.teampulse.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.teampulse.backend.dto.ApiResponse;
import com.teampulse.backend.dto.RegisterRequest;
import com.teampulse.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Validated @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(authService.register(request));
    }
}