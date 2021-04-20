package com.weather.api.controller;

import com.weather.api.model.AuthenticationResponse;
import com.weather.api.model.LoginRequest;
import com.weather.api.model.RegisterRequest;
import com.weather.api.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest request) {
        authService.signup(request);
        return new ResponseEntity<>("User Registration Successful!", HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
