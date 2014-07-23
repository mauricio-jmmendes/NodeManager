package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;
import com.nodemanager.model.Node;

public class NodeDAO extends GenericDAO<Node, Long>{
	
	public NodeDAO(EntityManager entityManager) {
		super(entityManager);
	}

}
