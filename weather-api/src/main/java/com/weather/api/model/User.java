package com.weather.api.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @Column(unique = true, nullable = false)
    @Email
    private String username;

    @Column(nullable = false)
    private String password;

    private String dob;

    @ManyToMany
    private List<WeatherData> weather = new ArrayList<>();
}
