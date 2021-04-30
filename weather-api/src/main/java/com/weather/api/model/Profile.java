package com.weather.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile implements Serializable {

    @Id
    @Column(unique = true)
    @Email(message = "Please provide valid emailId")
    private String username;

    @NotEmpty(message = "Password must not be empty")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return username.equals(profile.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
