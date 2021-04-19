package com.weather.api.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "weather_data")
@Data
public class WeatherData {

    @Id
    @GeneratedValue
    @Column(name = "city_name")
    private String cityName;

    @Column(name = "weather_description")
    private String weatherDescription;

    @Column(name = "sunrise")
    private Instant sunrise;

    @Column(name = "sunset")
    private Instant sunset;

    @Column(name = "current_temperature")
    private Double currentTemperature;

    @Column(name = "max_temperature")
    private Double maxTemperature;

    @Column(name = "min_temperature")
    private Double minTemperature;

    @ManyToMany(mappedBy = "weather")
    private List<Profile> profile = new ArrayList<>();
}
