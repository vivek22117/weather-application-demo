package com.weather.api.service;

import com.weather.api.model.RegisterRequest;
import com.weather.api.model.User;

import java.util.Optional;

public interface AuthService {

    Optional<User> signup(RegisterRequest request);
}
