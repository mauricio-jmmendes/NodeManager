package com.nodemanager.managedbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Hub;
import com.nodemanager.service.HubService;
import com.nodemanager.util.JPAUtil;

@ManagedBean
public class HubMB {

	private String nome;
	private HubService hubService;
	private Hub hub;

	private List<Hub> hubList;

	@PostConstruct
	public void init() {
		hub = new Hub();
		hubService = new HubService(JPAUtil.getSimpleEntityManager());
		hubList = list();

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Hub> list() {
		this.hubList = hubService.findAll();
		return hubList;

	}

	public void save() {
		hubService.save(hub);
		FacesUtils.addInfoMessage("HUB cadastrado com sucesso!");
		hub = new Hub();
		list();
	}

	public void prepareToEdit(Hub hub) {
		this.hub = hub;

	}

	public void delete() {
		hubService.delete(hub);
		FacesUtils.addInfoMessage("HUB removido com sucesso!");
		list();
	}

	public List<Hub> getHubList() {
		return hubList;
	}

	public void setHubList(List<Hub> hubList) {
		this.hubList = hubList;
	}

	public Hub getHub() {
		return hub;
	}

	public void setHub(Hub hub) {
		this.hub = hub;
	}
}