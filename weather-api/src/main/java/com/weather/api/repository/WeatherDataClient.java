package com.weather.api.repository;

import com.weather.api.model.WeatherData;

public interface WeatherDataClient {

    WeatherData getWeatherByCity(String cityName, String username);
}
