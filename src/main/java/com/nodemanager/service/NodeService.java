package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.NodeDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Node;

public class NodeService {

	private NodeDAO dao;

	private SimpleEntityManager simpleEntityManager;

	public NodeService(SimpleEntityManager simpleEntityManager) {
		this.simpleEntityManager = simpleEntityManager;
		dao = new NodeDAO(simpleEntityManager.getEntityManager());
	}

	public void save(Node node) {
		try {
			simpleEntityManager.beginTransaction();
			dao.save(node);
			simpleEntityManager.commit();
		} catch (Exception e) {
			e.printStackTrace();
			simpleEntityManager.rollBack();
		}
	}

	public List<Node> findAll() {
		return dao.findAll();
	}

}
