package com.nodemanager.dao.impl;

import javax.persistence.EntityManager;

import com.nodemanager.model.Slot;

public class SlotDAO extends GenericDAO<Slot, Long> {

	public SlotDAO(EntityManager entityManager) {
		super(entityManager);
	}
}
