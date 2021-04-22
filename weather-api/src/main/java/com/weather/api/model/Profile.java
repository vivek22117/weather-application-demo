package com.weather.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Profile {

    @Id
    @Column(unique = true, nullable = false)
    @Email
    private String username;

    @Column(nullable = false)
    private String password;

    private String dob;


    @ManyToMany
    @JoinTable(name = "profile_weather",
            joinColumns = {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "city_name")})
    private List<WeatherData> weather = new ArrayList<>();


    public void addWeatherData(WeatherData data) {
        this.weather.add(data);
        data.getProfile().add(this);
    }

    public void removeWeatherData(WeatherData data) {
        this.weather.remove(data);
        data.getProfile().remove(this);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
