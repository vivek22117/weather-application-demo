package com.weather.api.service;

import com.weather.api.model.*;

import java.util.Optional;

public interface AuthService {

    Profile signup(RegisterRequest request);

    AuthenticationResponse login(LoginRequest loginRequest);

    void logout(LogoutRequest logoutRequest);

    Profile getCurrentUser();
}
