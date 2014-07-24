package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.CmtsDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Cmts;

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
			dao.save(cmts);
			simpleEntityManager.commit();
		} catch (Exception e) {
			e.printStackTrace();
			simpleEntityManager.rollBack();
		}
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