package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.PlacaDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Placa;

public class PlacaService {

  private PlacaDAO dao;

  private SimpleEntityManager simpleEntityManager;

  public PlacaService(SimpleEntityManager simpleEntityManager) {
    this.simpleEntityManager = simpleEntityManager;
    dao = new PlacaDAO(simpleEntityManager.getEntityManager());
  }

  public void save(Placa placa) {
    try {
      simpleEntityManager.beginTransaction();
      dao.save(placa);
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }
  }

  public List<Placa> findAll() {
    return dao.findAll();
  }

}
