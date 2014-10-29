package com.nodemanager.service;

import java.sql.SQLException;
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

  public void save(Pattern pattern) throws SQLException {
    try {
      simpleEntityManager.beginTransaction();
      dao.save(pattern);

      simpleEntityManager.commit();
    } catch (Exception e) {
      simpleEntityManager.rollBack();
      throw new SQLException(e);
    }
  }

  public Pattern getById(Long id) {
    return dao.getById(id);
  }

  public List<Pattern> findAll() {
    return dao.findAll();
  }

  public List<Pattern> getByType(String type) {

    return dao.getByType(type);

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
