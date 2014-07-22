package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.DownstreamDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Downstream;

public class DownstreamService {
	private DownstreamDAO dao;

	private SimpleEntityManager simpleEntityManager;

	public DownstreamService(SimpleEntityManager simpleEntityManager) {
		this.simpleEntityManager = simpleEntityManager;
		dao = new DownstreamDAO(simpleEntityManager.getEntityManager());
	}

	public void save(Downstream downstream) {
		try {
			simpleEntityManager.beginTransaction();
			dao.save(downstream);
			simpleEntityManager.commit();
		} catch (Exception e) {
			e.printStackTrace();
			simpleEntityManager.rollBack();
		}
	}

	public List<Downstream> findAll() {
		return dao.findAll();
	}

}
