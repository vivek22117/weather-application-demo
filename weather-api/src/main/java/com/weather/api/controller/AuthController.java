package com.weather.api.controller;

import com.weather.api.model.AuthenticationResponse;
import com.weather.api.model.LoginRequest;
import com.weather.api.model.RegisterRequest;
import com.weather.api.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/signup", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> signUp(@Valid @RequestBody RegisterRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registration is successful for " + request.getUsername());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthenticationResponse authenticationResponse = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse);
    }
}
