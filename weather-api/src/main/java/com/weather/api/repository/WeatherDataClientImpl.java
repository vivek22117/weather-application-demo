package com.weather.api.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.weather.api.exception.WeatherDataNoFoundException;
import com.weather.api.model.WeatherData;
import com.weather.api.util.AppUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Component
public class WeatherDataClientImpl implements WeatherDataClient {

    private final RestTemplate restTemplate;
    private final Gson gson;

    public WeatherDataClientImpl(@Qualifier("weather-client") RestTemplate restTemplate, Gson gson) {
        this.restTemplate = restTemplate;
        this.gson = gson;
    }

    @Override
    public WeatherData getWeatherByCity(String cityName, String username) {
        log.debug("Requesting current weather for {}", cityName);
        ResponseEntity<String> response = null;

        try {
            String uri = AppUtility.WEATHER_ROOT_URL + "q=" + cityName + "&units=metric" + "&appid=" + AppUtility.API_KEY;

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        } catch (Exception ex) {
            throw new WeatherDataNoFoundException("No weather data found for city " + cityName);
        }

        return parseResponse(response.getBody()).orElseThrow(new Supplier<RuntimeException>() {
            @Override
            public RuntimeException get() {
                return new WeatherDataNoFoundException("No weather data found for city " + cityName);
            }
        });
    }

    private Optional<WeatherData> parseResponse(String body) {
        WeatherData weatherData = null;
        JsonParser jsonParser = new JsonParser();

        JsonElement jsonTree = jsonParser.parse(body);

        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            weatherData = new WeatherData();

            weatherData.setCityName(jsonObject.get("name").getAsString());
            weatherData.setWeatherDescription(jsonObject.get("weather").getAsJsonArray().get(0)
                    .getAsJsonObject().get("description").getAsString());
            weatherData.setCurrentTemperature(jsonObject.get("main").getAsJsonObject().get("temp").getAsDouble());
            weatherData.setMaxTemperature(jsonObject.get("main").getAsJsonObject().get("temp_max").getAsDouble());
            weatherData.setMinTemperature(jsonObject.get("main").getAsJsonObject().get("temp_min").getAsDouble());
            weatherData.setSunrise(jsonObject.get("sys").getAsJsonObject().get("sunrise").getAsLong());
            weatherData.setSunset(jsonObject.get("sys").getAsJsonObject().get("sunset").getAsLong());
        }

        return Optional.ofNullable(weatherData);
    }
}
