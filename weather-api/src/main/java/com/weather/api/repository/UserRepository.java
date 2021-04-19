package com.weather.api.repository;

import com.weather.api.model.Profile;

import java.util.Optional;

public interface UserRepository {

    void saveUser(Profile profile);

    Optional<Profile> getUserByUsername(String username);
}
