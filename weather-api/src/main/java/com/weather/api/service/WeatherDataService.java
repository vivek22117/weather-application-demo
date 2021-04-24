package com.weather.api.service;

import com.weather.api.exception.WeatherDataNoFoundException;
import com.weather.api.model.Profile;
import com.weather.api.model.WeatherResponse;

public interface WeatherDataService {

    WeatherResponse getWeatherData(String cityname, Profile currentUser) throws WeatherDataNoFoundException;

    void deleteWeatherHistory(String request);
}
