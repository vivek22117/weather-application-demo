package com.weather.api.repository;

import com.weather.api.model.WeatherData;

import java.util.List;
import java.util.Optional;

public interface WeatherDataRepository {

    void save(WeatherData weatherData);

    Optional<WeatherData> getByCityName(String cityName);

}
