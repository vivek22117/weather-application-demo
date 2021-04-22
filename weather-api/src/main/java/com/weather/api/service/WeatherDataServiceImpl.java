package com.weather.api.service;

import com.weather.api.model.Profile;
import com.weather.api.model.WeatherData;
import com.weather.api.model.WeatherDataDTO;
import com.weather.api.repository.ProfileRepository;
import com.weather.api.repository.WeatherDataClient;
import com.weather.api.repository.WeatherDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class WeatherDataServiceImpl implements WeatherDataService {

    private final WeatherDataClient dataClient;
    private final WeatherDataRepository weatherDataRepository;
    private final ProfileRepository profileRepository;

    public WeatherDataServiceImpl(WeatherDataClient dataClient, WeatherDataRepository weatherDataRepository,
                                  ProfileRepository profileRepository) {
        this.dataClient = dataClient;
        this.weatherDataRepository = weatherDataRepository;
        this.profileRepository = profileRepository;
    }


    @Override
    @Transactional
    public WeatherDataDTO getWeatherData(String cityName, Profile currentUser) {
        WeatherData weatherByCity = null;
        try {
            weatherByCity = dataClient.getWeatherByCity(cityName, currentUser.getUsername());
            weatherByCity.addProfile(currentUser);
            currentUser.addWeatherData(weatherByCity);
            profileRepository.saveUser(currentUser);
        } catch (Exception ex) {
            log.error("Error cause while saving weather data: " + ex.getMessage());
        }
        return mapDTO(weatherByCity);
    }

    private WeatherDataDTO mapDTO(WeatherData weatherByCity) {
        return WeatherDataDTO.builder()
                .weatherDescription(weatherByCity.getWeatherDescription())
                .cityName(weatherByCity.getCityName())
                .currentTemperature(weatherByCity.getCurrentTemperature())
                .maxTemperature(weatherByCity.getMaxTemperature())
                .minTemperature(weatherByCity.getMinTemperature())
                .sunrise(Instant.ofEpochMilli(weatherByCity.getSunrise() * 1000))
                .sunset(Instant.ofEpochMilli(weatherByCity.getSunset() * 1000))
                .build();
    }

    @Override
    public void deleteWeatherHistory(String request) {

    }
}
