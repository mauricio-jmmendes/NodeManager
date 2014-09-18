package com.nodemanager.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.nodemanager.model.Node;

public class NodeDAO extends GenericDAO<Node, Long> {

  public NodeDAO(EntityManager entityManager) {
    super(entityManager);
  }

  public List<Node> findByCodNode(String codNode) {
    return (List<Node>) getEntityManager()
        .createQuery("FROM Node n where n.codNode like :codNode", Node.class)
        .setParameter("codNode", "%" + codNode + "%").getResultList();

  }

}
