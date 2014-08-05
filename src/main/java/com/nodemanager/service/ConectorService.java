package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.ConectorDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Conector;

public class ConectorService {

  private ConectorDAO dao;

  private SimpleEntityManager simpleEntityManager;

  public ConectorService(SimpleEntityManager simpleEntityManager) {
    this.simpleEntityManager = simpleEntityManager;
    dao = new ConectorDAO(simpleEntityManager.getEntityManager());
  }

  public void save(Conector conector) {
    try {
      simpleEntityManager.beginTransaction();
      dao.save(conector);
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }
  }

  public List<Conector> findAll() {
    return dao.findAll();
  }

}
