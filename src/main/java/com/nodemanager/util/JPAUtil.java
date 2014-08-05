package com.nodemanager.util;

import com.nodemanager.dao.utils.SimpleEntityManager;

public class JPAUtil {

	private static final SimpleEntityManager simpleEntityManager = new SimpleEntityManager(
			"NodeManagerPU");

	public static SimpleEntityManager getSimpleEntityManager() {
		return simpleEntityManager;
	}
}