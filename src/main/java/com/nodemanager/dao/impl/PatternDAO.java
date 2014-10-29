package com.nodemanager.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.nodemanager.model.Pattern;


public class PatternDAO extends GenericDAO<Pattern, Long> {

  public PatternDAO(EntityManager entityManager) {
    super(entityManager);
  }

  public List<Pattern> getByType(String type) {

    return getEntityManager().createQuery("FROM Pattern p where p.type = :codType", Pattern.class)
        .setParameter("codType", type).getResultList();

  }
}
