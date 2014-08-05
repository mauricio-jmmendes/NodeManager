package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.HubDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Hub;

public class HubService {

  private HubDAO dao;

  private SimpleEntityManager simpleEntityManager;

  public HubService(SimpleEntityManager simpleEntityManager) {
    this.simpleEntityManager = simpleEntityManager;
    dao = new HubDAO(simpleEntityManager.getEntityManager());
  }

  public void save(Hub hub) {
    try {
      simpleEntityManager.beginTransaction();
      dao.save(hub);
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }
  }

  public List<Hub> findAll() {
    return dao.findAll();
  }

  public Hub getByCodHub(String codHub) {
    return dao.getByCodHub(codHub);
  }

  public void delete(Hub hub) {
    try {
      simpleEntityManager.beginTransaction();
      dao.delete(hub);
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }

  }

  public Hub getById(Long hubId) {

    return dao.getById(hubId);

  }
}
