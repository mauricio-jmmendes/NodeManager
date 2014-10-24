package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.PatternDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Pattern;

public class PatternService {
  private PatternDAO dao;

  private SimpleEntityManager simpleEntityManager;

  public PatternService(SimpleEntityManager simpleEntityManager) {
    this.simpleEntityManager = simpleEntityManager;
    dao = new PatternDAO(simpleEntityManager.getEntityManager());
  }

  public void save(Pattern pattern) {
    try {
      simpleEntityManager.beginTransaction();
      dao.save(pattern);

      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }
  }

  public Pattern getById(Long id) {
    return dao.getById(id);
  }

  public List<Pattern> findAll() {
    return dao.findAll();
  }

  public void update(Pattern pattern) {
    try {
      simpleEntityManager.beginTransaction();
      dao.update(pattern);
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }
  }

  public void delete(Pattern pattern) {

    try {
      simpleEntityManager.beginTransaction();
      dao.delete(pattern);
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }
  }
}
