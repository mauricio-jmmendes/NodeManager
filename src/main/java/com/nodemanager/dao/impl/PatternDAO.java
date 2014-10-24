package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;

import com.nodemanager.model.Pattern;


public class PatternDAO extends GenericDAO<Pattern, Long> {

  public PatternDAO(EntityManager entityManager) {
    super(entityManager);
  }
}
