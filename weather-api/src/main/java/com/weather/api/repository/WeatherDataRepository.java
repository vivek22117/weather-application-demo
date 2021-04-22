package com.weather.api.repository;

import com.weather.api.model.WeatherData;

import java.util.List;

public interface WeatherDataRepository {

   void save(WeatherData weatherData);

   List<WeatherData> fetchAllWeatherDataByUsername(String username);

   void updateWeatherData(String cityName);
}
