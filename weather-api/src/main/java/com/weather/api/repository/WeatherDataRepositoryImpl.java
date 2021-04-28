package com.weather.api.repository;

import com.weather.api.model.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class WeatherDataRepositoryImpl implements WeatherDataRepository {

    private final SessionFactory factory;

    public WeatherDataRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void save(WeatherData weatherData) {
        getSession().saveOrUpdate(weatherData);
    }

    @Override
    public Optional<WeatherData> getByCityName(String cityName) {
        return Optional.ofNullable(getSession().get(WeatherData.class, cityName));
    }

    private Session getSession() {
        Session session = factory.getCurrentSession();
        if (session == null) {
            session = factory.openSession();
        }
        return session;
    }
}
