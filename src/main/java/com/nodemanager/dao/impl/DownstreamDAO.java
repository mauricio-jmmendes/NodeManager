package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;
import com.nodemanager.model.Downstream;

public class DownstreamDAO extends GenericDAO<Downstream, Long>{
	
	public DownstreamDAO(EntityManager entityManager) {
		super(entityManager);
	}

}
