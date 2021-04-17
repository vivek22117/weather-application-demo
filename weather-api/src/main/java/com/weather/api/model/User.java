package com.weather.api.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private String userName;
    private String password;
    private Date dob;

    @ManyToMany
    private List<WeatherData> weather = new ArrayList<>();
}
