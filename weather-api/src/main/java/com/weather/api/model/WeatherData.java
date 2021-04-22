package com.weather.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "weather_data")
@Getter
@Setter
public class WeatherData {

    @Id
    @Column(name = "city_name")
    private String cityName;

    @Column(name = "weather_description")
    private String weatherDescription;

    @Column(name = "sunrise")
    private Long sunrise;

    @Column(name = "sunset")
    private Long sunset;

    @Column(name = "current_temperature")
    private Double currentTemperature;

    @Column(name = "max_temperature")
    private Double maxTemperature;

    @Column(name = "min_temperature")
    private Double minTemperature;

    @ManyToMany
    @JoinTable(name = "weather_profile",
            joinColumns = {@JoinColumn(name = "city_name")},
            inverseJoinColumns = {@JoinColumn(name = "username")})
    private Set<Profile> profile = new HashSet<>();

    public void addProfile(Profile data) {
        this.profile.add(data);
        data.getWeather().add(this);
    }

    public void removeProfile(Profile data) {
        this.profile.remove(data);
        data.getWeather().remove(this);
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "cityName='" + cityName + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", currentTemperature=" + currentTemperature +
                ", maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                '}';
    }
}
