package com.nodemanager.service;

import java.util.List;

import com.nodemanager.dao.impl.LoginDAO;
import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Login;

public class LoginService {

	private LoginDAO dao;

	private SimpleEntityManager simpleEntityManager;

	public LoginService(SimpleEntityManager simpleEntityManager) {
		this.simpleEntityManager = simpleEntityManager;
		dao = new LoginDAO(simpleEntityManager.getEntityManager());
	}

	public void save(Login login) {
		try {
			simpleEntityManager.beginTransaction();
			dao.save(login);
			simpleEntityManager.commit();
		} catch (Exception e) {
			e.printStackTrace();
			simpleEntityManager.rollBack();
		}
	}

	public List<Login> findAll() {
		return dao.findAll();
	}

}
