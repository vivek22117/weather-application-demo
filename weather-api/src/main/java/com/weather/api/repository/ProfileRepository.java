package com.weather.api.repository;

import com.weather.api.model.Profile;

import java.util.Optional;

public interface ProfileRepository {

    void saveUser(Profile profile);

    Optional<Profile> getUserByUsername(String username);

    void updateUser(Profile currentUser);
}
