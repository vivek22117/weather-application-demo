package com.weather.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeatherResponse {

    private WeatherDataDTO weatherDataDTO;
    private List<WeatherDataDTO> weatherDataDTOList;
}
