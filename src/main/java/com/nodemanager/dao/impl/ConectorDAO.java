package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;
import com.nodemanager.model.Conector;

public class ConectorDAO extends GenericDAO<Conector, Long>{
	
	public ConectorDAO(EntityManager entityManager) {
		super(entityManager);
	}

}
