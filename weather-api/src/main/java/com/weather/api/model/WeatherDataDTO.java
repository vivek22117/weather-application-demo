package com.weather.api.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class WeatherDataDTO {

    private String cityName;
    private String weatherDescription;
    private Instant sunrise;
    private Instant sunset;
    private Double currentTemperature;
    private Double maxTemperature;
    private Double minTemperature;
}
