package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;

import com.nodemanager.model.Hub;

public class HubDAO extends GenericDAO<Hub, Long> {

  public HubDAO(EntityManager entityManager) {
    super(entityManager);
  }

  public Hub getByCodHub(String codHub) {

    return (Hub) getEntityManager().createQuery("FROM Hub h where h.codHub = :codHub", Hub.class)
        .setParameter("codHub", codHub);
  }
}
