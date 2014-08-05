package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Cmts;
import com.nodemanager.model.Hub;
import com.nodemanager.service.CmtsService;
import com.nodemanager.service.HubService;

@ManagedBean
@SessionScoped
public class CmtsMB {

  String persistenceUnitName = "NodeManagerPU";

  SimpleEntityManager simpleEntityManager;

  private CmtsService cmtsService;

  private HubService hubService;

  private Cmts cmts;

  private List<Hub> hubs;

  private Long hubId;

  private List<Cmts> cmtsList = new ArrayList<Cmts>();

  @PostConstruct
  public void init() {
    simpleEntityManager = new SimpleEntityManager(persistenceUnitName);
    cmtsService = new CmtsService(simpleEntityManager);
    hubService = new HubService(simpleEntityManager);
    cmts = new Cmts();
    list();
  }

  public void list() {
    cmtsList = cmtsService.findAll();
  }

  public void prepareToAdd() {
    this.cmts = new Cmts();
    this.hubs = hubService.findAll();
  }

  public void save() {
    cmts.setHub(hubService.getById(hubId));
    cmtsService.save(cmts);
    FacesUtils.addInfoMessage("CMTS cadastrado com sucesso!");
    FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    list();
    this.cmts = new Cmts();
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

  /**
   * @return the hub
   */
  public List<Hub> getHubs() {
    return hubs;
  }

  /**
   * @param hub the hub to set
   */
  public void setHubs(List<Hub> hubs) {
    this.hubs = hubs;
  }

  /**
   * @return the hubId
   */
  public Long getHubId() {
    return hubId;
  }

  /**
   * @param hubId the hubId to set
   */
  public void setHubId(Long hubId) {
    this.hubId = hubId;
  }
}
