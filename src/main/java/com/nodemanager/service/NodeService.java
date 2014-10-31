package com.nodemanager.service;

import java.sql.SQLException;
import java.util.List;

import com.nodemanager.dao.impl.NodeDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Node;

public class NodeService {

  private NodeDAO dao;

  private SimpleEntityManager simpleEntityManager;

  public NodeService(SimpleEntityManager simpleEntityManager) {
    this.simpleEntityManager = simpleEntityManager;
    dao = new NodeDAO(simpleEntityManager.getEntityManager());
  }

  public void save(Node node) throws SQLException {
    try {
      simpleEntityManager.beginTransaction();
      dao.save(node);
      simpleEntityManager.commit();
    } catch (Exception e) {
      simpleEntityManager.rollBack();
      throw new SQLException(e);
    }
  }

  public List<Node> findAll() {
    return dao.findAll();
  }

  public List<Node> findByCodNode(String codNode) {
    return dao.findByCodNode(codNode);
  }

  public void delete(Node node) throws SQLException {
    try {
      simpleEntityManager.beginTransaction();
      dao.delete(node);
      simpleEntityManager.commit();
    } catch (Exception e) {
      simpleEntityManager.rollBack();
      throw new SQLException(e);
    }
  }

  public void update(Node node) throws SQLException {
    try {
      simpleEntityManager.beginTransaction();
      dao.update(node);
      simpleEntityManager.commit();
    } catch (Exception e) {
      simpleEntityManager.rollBack();
      throw new SQLException(e);
    }
  }
}
