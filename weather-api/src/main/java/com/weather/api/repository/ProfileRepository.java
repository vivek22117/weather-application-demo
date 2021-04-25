package com.weather.api.repository;

import com.weather.api.model.Profile;
import com.weather.api.model.WeatherData;

import java.util.Optional;
import java.util.Set;

public interface ProfileRepository {

    void saveUser(Profile profile);

    Optional<Profile> getUserByUsername(String username);

    void updateUser(Profile currentUser);

    Optional<Set<WeatherData>> getWeatherHistoryByUser(String username);
}
