package com.weather.api.service;

import com.weather.api.model.Profile;
import com.weather.api.model.ProfileHistoryResponse;
import com.weather.api.model.WeatherResponse;

public interface WeatherDataService {

    WeatherResponse getWeatherData(String cityName, Profile currentUser);

    WeatherResponse getWeatherHistory(String currentUser);

    ProfileHistoryResponse getUserHistory(String cityName);

    void deleteWeatherHistory(String cityName, String username);
}
