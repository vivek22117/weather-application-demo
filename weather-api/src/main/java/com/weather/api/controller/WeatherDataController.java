package com.weather.api.controller;

import com.weather.api.model.Profile;
import com.weather.api.model.WeatherResponse;
import com.weather.api.service.AuthService;
import com.weather.api.service.WeatherDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.weather.api.util.AppUtility.*;

@RestController
@RequestMapping(WEATHER_API_ROOT_MAPPING)
public class WeatherDataController {

    private final WeatherDataService weatherDataService;
    private final AuthService authService;

    public WeatherDataController(WeatherDataService weatherDataService, AuthService authService) {
        this.weatherDataService = weatherDataService;
        this.authService = authService;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = WEATHER_CITY_API, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WeatherResponse> getWeatherData(@PathVariable String city) {
        Profile currentUser = authService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(weatherDataService.getWeatherData(city, currentUser));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = WEATHER_HISTORY_API, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WeatherResponse> getWeatherHistory(@RequestParam(value = "username") String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(weatherDataService.getWeatherHistory(username));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping(value = WEATHER_DELETE_API, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteWeatherData(@RequestParam(value = "city") String city,
                                                    @RequestParam(value = "username") String username) {
        weatherDataService.deleteWeatherHistory(city, username);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Weather history for city " + city + " against user " + username + "is deleted successfully!");
    }
}
