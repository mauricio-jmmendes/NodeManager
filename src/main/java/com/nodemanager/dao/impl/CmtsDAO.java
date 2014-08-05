package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;

import com.nodemanager.model.Cmts;

public class CmtsDAO extends GenericDAO<Cmts, Long> {

  public CmtsDAO(EntityManager entityManager) {
    super(entityManager);
  }
}
