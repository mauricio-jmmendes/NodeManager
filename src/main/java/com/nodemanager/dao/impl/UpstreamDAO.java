package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;
import com.nodemanager.model.Upstream;

public class UpstreamDAO extends GenericDAO<Upstream, Long>{

	public UpstreamDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
}
