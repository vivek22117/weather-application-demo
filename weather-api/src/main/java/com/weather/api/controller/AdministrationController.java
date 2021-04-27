package com.weather.api.controller;

import com.weather.api.model.ProfileHistoryResponse;
import com.weather.api.model.WeatherDataDTO;
import com.weather.api.model.WeatherHistoryResponse;
import com.weather.api.service.WeatherDataService;
import org.apache.commons.text.WordUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.weather.api.util.AppUtility.*;

@RestController
@RequestMapping(value = ADMIN_ROOT_API)
public class AdministrationController {

    private final WeatherDataService weatherDataService;

    public AdministrationController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = ADMIN_API_CITY_HISTORY, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<WeatherHistoryResponse> getWeatherHistoryByUser(@RequestParam(value = "username") String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(WeatherHistoryResponse.builder().cityList(weatherDataService.getWeatherHistory(username)
                        .getWeatherDataDTOList().stream()
                        .map(WeatherDataDTO::getCityName).collect(Collectors.toList())).build());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = ADMIN_API_USER_HISTORY, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProfileHistoryResponse> getUserHistoryByCity(@RequestParam(value = "city") String city) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(weatherDataService.getUserHistory(WordUtils.capitalizeFully(city)));
    }
}
