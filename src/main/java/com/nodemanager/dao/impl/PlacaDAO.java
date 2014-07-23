package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;
import com.nodemanager.model.Placa;

public class PlacaDAO extends GenericDAO<Placa, Long>{
	
	public PlacaDAO(EntityManager entityManager) {
		super(entityManager);
	}

}
