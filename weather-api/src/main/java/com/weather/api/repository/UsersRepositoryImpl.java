package com.weather.api.repository;

import com.weather.api.model.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UserRepository{

  private final SessionFactory factory;

  public UsersRepositoryImpl(SessionFactory factory) {
    this.factory = factory;
  }


  @Override
  public void saveUser(Profile profile) {
    getSession().save(profile);
  }

  @Override
  public Optional<Profile> getUserByUsername(String username) {
    Query<Profile> query = getSession().createQuery("FROM User u where u.username=:username", Profile.class);
    query.setParameter("username", username);
    return Optional.of(query.uniqueResult());
  }

  private Session getSession() {
    Session session = factory.getCurrentSession();
    if(session == null) {
      session = factory.openSession();
    }
    return session;
  }
}