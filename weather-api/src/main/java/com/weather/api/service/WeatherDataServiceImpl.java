package com.weather.api.service;

import com.weather.api.exception.BusinessException;
import com.weather.api.exception.WeatherDataNoFoundException;
import com.weather.api.model.*;
import com.weather.api.repository.ProfileRepository;
import com.weather.api.repository.WeatherDataClient;
import com.weather.api.repository.WeatherDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

                mapWeatherData(weatherDataFromAPI, weatherData);

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
            log.error("Error cause while fetching weather data for city: " + cityName);
            throw new WeatherDataNoFoundException(ex.getMessage());
        } catch (Exception ex) {
            log.error("Failed to save weather data in DB");
            throw new BusinessException(ex.getMessage());
        }
        return setWeatherResponse(weatherDataFromAPI, weatherSearchHistory);
    }

    @Override
    @Transactional
    public WeatherResponse getWeatherHistory(String currentUser) {
        log.debug("Weather history API execution for user {}", currentUser);

        try {
            Optional<Profile> weatherHistoryByUser =
                    profileRepository.getWeatherHistoryByUser(currentUser);
            if (weatherHistoryByUser.isPresent()) {
                Set<WeatherData> weatherData = weatherHistoryByUser.get().getWeather();

                return WeatherResponse.builder()
                        .weatherDataDTO(null)
                        .weatherDataDTOList(weatherData.stream()
                                .map(this::mapDTO)
                                .collect(Collectors.toList())).build();
            }

        } catch (Exception ex) {
            log.error("Failed to fetch weather history data in DB");
            throw new BusinessException(ex.getMessage());
        }
        return WeatherResponse.builder().weatherDataDTO(null).weatherDataDTOList(new ArrayList<>()).build();
    }

    @Override
    @Transactional
    public ProfileHistoryResponse getUserHistory(String cityName) {
        log.debug("User history API execution for city {}", cityName);

        try {
            Optional<WeatherData> userHistoryByCityName = profileRepository.getUserHistoryByCityName(cityName);
            if (userHistoryByCityName.isPresent()) {
                Set<Profile> profiles = userHistoryByCityName.get().getProfile();

                return ProfileHistoryResponse.builder()
                        .profileList(profiles.stream()
                                .map(Profile::getUsername)
                                .collect(Collectors.toList())).build();
            }

        } catch (Exception ex) {
            log.error("Failed to fetch user history data");
            throw new BusinessException(ex.getMessage());
        }
        return ProfileHistoryResponse.builder()
                .profileList(new ArrayList<>()).build();
    }

    @Override
    @Transactional
    public void deleteWeatherHistory(String cityName, String username) {
        Optional<Profile> userByUsername = profileRepository.getUserByUsername(username);
        if (userByUsername.isPresent()) {
            Profile profile = userByUsername.get();
            Optional<WeatherData> byCityName = weatherDataRepository.getByCityName(cityName);
            if (byCityName.isPresent()) {
                WeatherData weatherData = byCityName.get();
                weatherData.removeProfile(profile);
                profile.removeWeatherData(weatherData);
            }

        }
        log.debug("successfully removed!");
    }

    private void mapWeatherData(WeatherData weatherDataFromAPI, WeatherData weatherData) {
        weatherData.setCityName(weatherDataFromAPI.getCityName());
        weatherData.setSunset(weatherDataFromAPI.getSunset());
        weatherData.setWeatherDescription(weatherDataFromAPI.getWeatherDescription());
        weatherData.setMinTemperature(weatherDataFromAPI.getMinTemperature());
        weatherData.setMaxTemperature(weatherDataFromAPI.getMaxTemperature());
        weatherData.setSunrise(weatherDataFromAPI.getSunrise());
        weatherData.setCurrentTemperature(weatherDataFromAPI.getCurrentTemperature());
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
}
