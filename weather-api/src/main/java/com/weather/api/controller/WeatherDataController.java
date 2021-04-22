package com.weather.api.controller;

import com.weather.api.model.Profile;
import com.weather.api.model.WeatherDataDTO;
import com.weather.api.service.AuthService;
import com.weather.api.service.WeatherDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@Slf4j
public class WeatherDataController {

    private final WeatherDataService weatherDataService;
    private final AuthService authService;

    public WeatherDataController(WeatherDataService weatherDataService, AuthService authService) {
        this.weatherDataService = weatherDataService;
        this.authService = authService;
    }


    @GetMapping(value = "/{city}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WeatherDataDTO> getWeatherData(@PathVariable String city) {
        Profile currentUser = authService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(weatherDataService.getWeatherData(city, currentUser));
    }

}
