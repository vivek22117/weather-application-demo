package com.weather.api.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class WeatherHistoryResponse implements Serializable {

    private List<String> cityList;
}
