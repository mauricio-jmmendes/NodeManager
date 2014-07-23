package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.UpstreamDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Upstream;

public class UpstreamService {

	private UpstreamDAO dao;

	private SimpleEntityManager simpleEntityManager;

	public UpstreamService(SimpleEntityManager simpleEntityManager) {
		this.simpleEntityManager = simpleEntityManager;
		dao = new UpstreamDAO(simpleEntityManager.getEntityManager());
	}

	public void save(Upstream upstream) {
		try {
			simpleEntityManager.beginTransaction();
			dao.save(upstream);
			simpleEntityManager.commit();
		} catch (Exception e) {
			e.printStackTrace();
			simpleEntityManager.rollBack();
		}
	}

	public List<Upstream> findAll() {
		return dao.findAll();
	}

}
