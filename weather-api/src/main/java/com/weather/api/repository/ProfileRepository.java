package com.weather.api.repository;

import com.weather.api.model.Profile;
import com.weather.api.model.WeatherData;

import java.util.Optional;

public interface ProfileRepository {

    void saveUser(Profile profile);

    Optional<Profile> getUserByUsername(String username);

    Optional<Profile> getWeatherHistoryByUser(String username);

    Optional<WeatherData> getUserHistoryByCityName(String cityName);
}
