package com.weather.api.service;

import com.weather.api.exception.BusinessException;
import com.weather.api.exception.WeatherDataNoFoundException;
import com.weather.api.model.Profile;
import com.weather.api.model.WeatherData;
import com.weather.api.model.WeatherDataDTO;
import com.weather.api.model.WeatherResponse;
import com.weather.api.repository.ProfileRepository;
import com.weather.api.repository.WeatherDataClient;
import com.weather.api.repository.WeatherDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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
    public WeatherResponse getWeatherData(String cityName, Profile currentUser) {
        log.debug("Weather API service layer execution for city {}/{} ", cityName, currentUser.getUsername());

        WeatherData weatherDataFromAPI = null;
        Set<WeatherData> weatherSearchHistory = new HashSet<>();

        try {
            weatherDataFromAPI = dataClient.getWeatherByCity(cityName, currentUser.getUsername());

            Optional<WeatherData> weatherDataFromDB = weatherDataRepository.getByCityName(cityName);
            if (weatherDataRepository.getByCityName(cityName).isPresent()) {
                WeatherData weatherData = weatherDataFromDB.get();
                weatherData.setCityName(weatherDataFromAPI.getCityName());
                weatherData.setSunset(weatherDataFromAPI.getSunset());
                weatherData.setWeatherDescription(weatherDataFromAPI.getWeatherDescription());
                weatherData.setMinTemperature(weatherDataFromAPI.getMinTemperature());
                weatherData.setMaxTemperature(weatherDataFromAPI.getMaxTemperature());
                weatherData.setSunrise(weatherDataFromAPI.getSunrise());
                weatherData.setCurrentTemperature(weatherDataFromAPI.getCurrentTemperature());

                weatherData.addProfile(currentUser);
                currentUser.addWeatherData(weatherData);
                weatherSearchHistory.addAll(currentUser.getWeather());
                weatherDataRepository.save(weatherData);
                return setWeatherResponse(weatherData, weatherSearchHistory);
            } else {
                currentUser.addWeatherData(weatherDataFromAPI);
                weatherSearchHistory.addAll(currentUser.getWeather());
                weatherDataRepository.save(weatherDataFromAPI);
            }

        } catch (WeatherDataNoFoundException ex) {
            log.error("Error cause while saving or fetching weather data for city: " + cityName);
            throw new WeatherDataNoFoundException(ex.getMessage());
        } catch (Exception ex) {
            log.error("Failed to save weather data in DB");
            throw new BusinessException(ex.getMessage());
        }
        return setWeatherResponse(weatherDataFromAPI, weatherSearchHistory);
    }

    private WeatherResponse setWeatherResponse(WeatherData weatherData, Set<WeatherData> weatherSearchHistory) {
        return WeatherResponse.builder().weatherDataDTO(mapDTO(weatherData))
                .weatherDataDTOList(weatherSearchHistory
                        .stream()
                        .map(this::mapDTO)
                        .collect(Collectors.toList()))
                .build();
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
