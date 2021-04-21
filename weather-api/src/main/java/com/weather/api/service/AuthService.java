package com.weather.api.service;

import com.weather.api.model.AuthenticationResponse;
import com.weather.api.model.LoginRequest;
import com.weather.api.model.RegisterRequest;
import com.weather.api.model.Profile;

import java.util.Optional;

public interface AuthService {

    Profile signup(RegisterRequest request);

    AuthenticationResponse login(LoginRequest loginRequest);

    void logout(Profile profile);

    Profile getCurrentUser();
}
