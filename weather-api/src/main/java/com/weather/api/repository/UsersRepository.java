package com.weather.api.repository;

import com.weather.api.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRepository {

  private final SessionFactory factory;

  public UsersRepository(SessionFactory factory) {
    this.factory = factory;
  }

  public void saveUser(User user) {
    getSession().save(user);
  }

  public void getWeatherHistory(User user) {

  }

  private Session getSession() {
    Session session = factory.getCurrentSession();
    if(session == null) {
      session = factory.openSession();
    }
    return session;
  }

}