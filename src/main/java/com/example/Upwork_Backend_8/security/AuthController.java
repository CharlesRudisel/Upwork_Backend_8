package com.example.Upwork_Backend_8.security;

import com.example.Upwork_Backend_8.security.dto.AuthRequest;
import com.example.Upwork_Backend_8.security.dto.AuthResponse;
import com.example.Upwork_Backend_8.security.dto.RegisterRequest;
import com.example.Upwork_Backend_8.security.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(service.authenticate(authRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerAndGetToken(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }
}
