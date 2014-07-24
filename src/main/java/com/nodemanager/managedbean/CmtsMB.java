package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Cmts;
import com.nodemanager.service.CmtsService;

@ManagedBean
public class CmtsMB {

	String persistenceUnitName = "NodeManagerPU";

	SimpleEntityManager simpleEntityManager;

	private CmtsService cmtsService;

	private Cmts cmts;

	private List<Cmts> cmtsList = new ArrayList<Cmts>();

	public CmtsMB() {
		simpleEntityManager = new SimpleEntityManager(persistenceUnitName);
		cmtsService = new CmtsService(simpleEntityManager);
		cmts = new Cmts();
		list();
	}

	public void list() {
		cmtsList = cmtsService.findAll();
	}

	public void prepareToAdd() {
		this.cmts = new Cmts();
	}

	public void save() {
		cmtsService.save(cmts);
		FacesUtils.addInfoMessage("Usuário adicionado com sucesso!");
		list();
	}

	public void delete() {
		cmtsService.delete(cmts);
		FacesUtils.addInfoMessage("CMTS removido com sucesso!");
		list();
	}

	public void prepareToUpdate(Cmts cmts) {
		this.cmts = cmtsService.getById(cmts.getId());
	}

	public void update() {

		cmtsService.update(cmts);
		FacesUtils.addInfoMessage("CMTS atualizado com sucesso!");
		list();
	}

	public Cmts getCmts() {
		return cmts;
	}

	public void setCmts(Cmts cmts) {
		this.cmts = cmts;
	}

	public List<Cmts> getCmtsList() {
		return cmtsList;
	}

	public void setCmtsList(List<Cmts> cmtsList) {
		this.cmtsList = cmtsList;
	}
}
