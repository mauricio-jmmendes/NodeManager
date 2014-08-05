package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;

import com.nodemanager.model.Login;

public class LoginDAO extends GenericDAO<Login, Long> {

  public LoginDAO(EntityManager entityManager) {
    super(entityManager);
  }

}
