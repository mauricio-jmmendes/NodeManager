package com.nodemanager.service;

import java.util.ArrayList;
import java.util.List;

import com.nodemanager.dao.impl.CmtsDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Cmts;
import com.nodemanager.model.Slot;
import com.nodemanager.util.Status;

public class CmtsService {
  private CmtsDAO dao;

  private SimpleEntityManager simpleEntityManager;

  public CmtsService(SimpleEntityManager simpleEntityManager) {
    this.simpleEntityManager = simpleEntityManager;
    dao = new CmtsDAO(simpleEntityManager.getEntityManager());
  }

  public void save(Cmts cmts) {
    try {
      simpleEntityManager.beginTransaction();
      createSlots(cmts);
      dao.save(cmts);
      
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }
  }

  /**
   * @param cria os slots do CMTS
   */
  private void createSlots(Cmts cmts) {
    List<Slot> slots = new ArrayList<Slot>();

    for (int i = 1; i < 11; i++) {
      Slot slot = new Slot();
      slot.setCmts(cmts);
      slot.setCodSlot(i + "/0");
      slot.setStatusSlot(Status.LIVRE);

      slots.add(slot);

    }

    cmts.setSlots(slots);
  }

  public Cmts getById(Long id) {
    return dao.getById(id);
  }

  public List<Cmts> findAll() {
    return dao.findAll();
  }

  public void update(Cmts cmts) {
    try {
      simpleEntityManager.beginTransaction();
      dao.update(cmts);
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }

  }

  public void delete(Cmts cmts) {

    try {
      simpleEntityManager.beginTransaction();
      dao.delete(cmts);
      simpleEntityManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      simpleEntityManager.rollBack();
    }
  }
}
