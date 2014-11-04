package com.nodemanager.service;

import java.sql.SQLException;
import java.util.List;

import com.nodemanager.dao.impl.ProjetoDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Projeto;

public class ProjetoService {

  private ProjetoDAO dao;

  private SimpleEntityManager simpleEntityManager;

  public ProjetoService(SimpleEntityManager simpleEntityManager) {
    this.simpleEntityManager = simpleEntityManager;
    dao = new ProjetoDAO(simpleEntityManager.getEntityManager());
  }

  public void save(Projeto projeto) throws SQLException {
    try {
      simpleEntityManager.beginTransaction();
      dao.save(projeto);
      simpleEntityManager.commit();
    } catch (Exception e) {
      simpleEntityManager.rollBack();
      throw new SQLException(e);
    }
  }

  public Projeto getById(Long id) {
    return dao.getById(id);
  }

  public List<Projeto> findAll() {
    return dao.findAll();
  }

  public List<Projeto> getByNode(String node) {

    return dao.getByNode(node);

  }

  public void update(Projeto projeto) throws SQLException {
    try {
      simpleEntityManager.beginTransaction();
      dao.update(projeto);
      simpleEntityManager.commit();
    } catch (Exception e) {
      simpleEntityManager.rollBack();
      throw new SQLException(e);
    }
  }

  public void delete(Projeto projeto) throws SQLException {
    try {
      simpleEntityManager.beginTransaction();
      dao.delete(projeto);
      simpleEntityManager.commit();
    } catch (Exception e) {
      simpleEntityManager.rollBack();
      throw new SQLException(e);
    }
  }
}
