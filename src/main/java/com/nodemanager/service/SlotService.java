package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.SlotDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Slot;

public class SlotService {
	private SlotDAO dao;

	private SimpleEntityManager simpleEntityManager;

	public SlotService(SimpleEntityManager simpleEntityManager) {
		this.simpleEntityManager = simpleEntityManager;
		dao = new SlotDAO(simpleEntityManager.getEntityManager());
	}

	public void save(Slot slot) {
		try {
			simpleEntityManager.beginTransaction();
			dao.save(slot);
			simpleEntityManager.commit();
		} catch (Exception e) {
			e.printStackTrace();
			simpleEntityManager.rollBack();
		}
	}

	public List<Slot> findAll() {
		return dao.findAll();
	}
}