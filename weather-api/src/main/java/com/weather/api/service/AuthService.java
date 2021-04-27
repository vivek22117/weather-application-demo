package com.weather.api.service;

import com.weather.api.model.AuthenticationResponse;
import com.weather.api.model.LoginRequest;
import com.weather.api.model.Profile;
import com.weather.api.model.RegisterRequest;

public interface AuthService {

    Profile signup(RegisterRequest request);

    AuthenticationResponse login(LoginRequest loginRequest);

    Profile getCurrentUser();
}
