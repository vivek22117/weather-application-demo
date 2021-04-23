package com.weather.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Profile {

    @Id
    @Column(unique = true, nullable = false)
    @Email(message = "Please provide valid emailId")
    private String username;

    @Column(nullable = false)
    private String password;

    @Past
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "profile")
    private Set<WeatherData> weather = new HashSet<>();


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
                ", dob=" + dob +
                '}';
    }
}
