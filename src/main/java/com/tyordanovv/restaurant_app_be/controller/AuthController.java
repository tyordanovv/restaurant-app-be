package com.tyordanovv.restaurant_app_be.controller;
import com.tyordanovv.restaurant_app_be.dto.LoginRequest;
import com.tyordanovv.restaurant_app_be.dto.LoginResponse;
import com.tyordanovv.restaurant_app_be.dto.RegisterRequest;
import com.tyordanovv.restaurant_app_be.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request.email(), request.password()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.email(), request.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}