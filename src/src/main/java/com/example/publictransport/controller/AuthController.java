package com.example.publictransport.controller;

import com.example.publictransport.dto.request.LoginRequest;
import com.example.publictransport.dto.request.RegisterRequest;
import com.example.publictransport.dto.response.AuthResponse;
import com.example.publictransport.service.AuthService;
import com.example.publictransport.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        String token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .username(request.getUsername())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .username(request.getUsername())
                .build());
    }
}
