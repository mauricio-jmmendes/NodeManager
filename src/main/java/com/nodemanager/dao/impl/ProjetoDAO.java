package com.nodemanager.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.nodemanager.model.Projeto;


public class ProjetoDAO extends GenericDAO<Projeto, Long> {

  public ProjetoDAO(EntityManager entityManager) {
    super(entityManager);
  }

  public List<Projeto> getByNode(String node) {

    return getEntityManager()
        .createQuery("FROM Projeto p where p.nodeDe = :codNode OR p.nodePara = :codNode",
            Projeto.class).setParameter("codNode", node).getResultList();

  }

}
