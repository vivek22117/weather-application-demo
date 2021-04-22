package com.weather.api.service;

import com.weather.api.model.Profile;
import com.weather.api.model.WeatherDataDTO;

public interface WeatherDataService {

    WeatherDataDTO getWeatherData(String cityname, Profile currentUser);

    void deleteWeatherHistory(String request);
}
