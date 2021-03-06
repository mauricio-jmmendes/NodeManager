package com.nodemanager.service;

import java.sql.SQLException;
import java.util.List;

import com.nodemanager.dao.impl.PlacaDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Placa;
import com.nodemanager.util.Status;

public class PlacaService {

  private PlacaDAO dao;

  private SimpleEntityManager simpleEntityManager;

  public PlacaService(SimpleEntityManager simpleEntityManager) {
    this.simpleEntityManager = simpleEntityManager;
    dao = new PlacaDAO(simpleEntityManager.getEntityManager());
  }

  public void save(Placa placa) throws SQLException {
    try {
      simpleEntityManager.beginTransaction();
      dao.save(placa);
      simpleEntityManager.commit();
    } catch (Exception e) {
      simpleEntityManager.rollBack();
      throw new SQLException(e);
    }
  }

  public List<Placa> findAll() {
    return dao.findAll();
  }

  public List<Placa> findPlacaByUpstreamStatus(List<Long> cmtsIds, Long qtdUpstream, Status soUpsLivres) {
    return dao.findPlacaByUpstreamStatus(cmtsIds, qtdUpstream, soUpsLivres);
  }
  
  public List<Placa> findPlacaByDownstreamStatus(Long qtdDownstream, Status statusDownstream) {
    return dao.findPlacaByDownstreamStatus(qtdDownstream, statusDownstream);
  }

  

  public void delete(Placa placa) {
    try {
      simpleEntityManager.beginTransaction();
      dao.delete(placa);
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }
  }

  public void update(Placa placa) throws SQLException {
    try {
      simpleEntityManager.beginTransaction();
      dao.update(placa);
      simpleEntityManager.commit();
    } catch (Exception e) {
      simpleEntityManager.rollBack();
      throw new SQLException(e);
    }
  }


}
