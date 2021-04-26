package com.weather.api.repository;

import com.weather.api.model.Profile;
import com.weather.api.model.WeatherData;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@Slf4j
public class ProfileRepositoryImpl implements ProfileRepository {

    private final SessionFactory factory;

    public ProfileRepositoryImpl(SessionFactory factory) {
        this.factory = factory;
    }


    @Override
    public void saveUser(Profile profile) {
        getSession().saveOrUpdate(profile);
    }

    @Override
    public Optional<Profile> getUserByUsername(String username) {
        Query<Profile> query = getSession().createQuery("FROM Profile where username=:username", Profile.class);
        query.setParameter("username", username);
        return Optional.of(query.uniqueResult());
    }

    @Override
    public Optional<Profile> getWeatherHistoryByUser(String username) {
        Query<Profile> query = getSession().createQuery("FROM Profile WHERE username=:username", Profile.class);
        query.setParameter("username", username);
        return Optional.ofNullable(query.uniqueResult());
    }

    @Override
    public Optional<WeatherData> getUserHistoryByCityName(String cityName) {
        Query<WeatherData> query = getSession().createQuery("FROM WeatherData WHERE cityName=:cityName", WeatherData.class);
        query.setParameter("cityName", cityName);
        return Optional.ofNullable(query.uniqueResult());
    }

    private Session getSession() {
        Session session = factory.getCurrentSession();
        if (session == null) {
            session = factory.openSession();
        }
        return session;
    }
}