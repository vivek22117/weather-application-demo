package com.weather.api.repository;

import com.weather.api.model.Profile;
import com.weather.api.model.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Repository
public class WeatherDataRepositoryImpl implements WeatherDataRepository {

    private final SessionFactory factory;

    public WeatherDataRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(WeatherData weatherData) {
        WeatherData savedWeatherData = getSession().get(WeatherData.class, weatherData.getCityName());
        if(ObjectUtils.isEmpty(savedWeatherData)) {
            getSession().save(weatherData);
        } else {
            getSession().update(weatherData);
        }
    }

    @Override
    public List<WeatherData> fetchAllWeatherDataByUsername(String username) {
        return null;
    }

    @Override
    public void updateWeatherData(String cityName) {

    }

    private Session getSession() {
        Session session = factory.getCurrentSession();
        if(session == null) {
            session = factory.openSession();
        }
        return session;
    }
}
