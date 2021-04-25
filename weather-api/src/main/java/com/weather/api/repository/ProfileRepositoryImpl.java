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
    try {
      getSession().saveOrUpdate(profile);
    } catch (Exception ex) {
      log.error("Error");
    }
  }

  @Override
  public Optional<Profile> getUserByUsername(String username) {
    Query<Profile> query = getSession().createQuery("FROM Profile where username=:username", Profile.class);
    query.setParameter("username", username);
    return Optional.of(query.uniqueResult());
  }

  @Override
  public void updateUser(Profile currentUser) {
    getSession().update(currentUser);
  }

  @Override
  public Optional<Set<WeatherData>> getWeatherHistoryByUser(String username) {
    Query<Profile> query = getSession().createQuery("FROM Profile WHERE username=:username", Profile.class);
    query.setParameter("username", username);
    return Optional.ofNullable(query.uniqueResult().getWeather());
  }

  private Session getSession() {
    Session session = factory.getCurrentSession();
    if(session == null) {
      session = factory.openSession();
    }
    return session;
  }
}