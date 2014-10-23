package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Cmts;
import com.nodemanager.model.Hub;
import com.nodemanager.service.CmtsService;
import com.nodemanager.service.HubService;
import com.nodemanager.util.JPAUtil;

@ManagedBean
@RequestScoped
public class CmtsMB {

  private CmtsService cmtsService;

  private HubService hubService;

  private Cmts cmts;

  private String slotList;

  private List<Hub> hubs;

  private Long hubId;

  private List<Cmts> cmtsList = new ArrayList<Cmts>();

  @PostConstruct
  public void init() {
    cmtsService = new CmtsService(JPAUtil.getSimpleEntityManager());
    hubService = new HubService(JPAUtil.getSimpleEntityManager());
    cmts = new Cmts();
    hubs = hubService.findAll();
    list();
  }

  public void list() {
    cmtsList = cmtsService.findAll();
  }

  public void prepareToAdd() {
    this.cmts = new Cmts();
    this.hubs = hubService.findAll();
  }

  public List<String> getSlotListFromString() {
    StringTokenizer stringTokenizer = new StringTokenizer(slotList, ", ");

    List<String> slotList = new ArrayList<>(this.slotList.length());

    while (stringTokenizer.hasMoreElements()) { // 0 1 2 ...
      slotList.add(stringTokenizer.nextToken());
    }

    return slotList;
  }

  public void save() {
    Hub hub = hubService.getById(hubId);
    cmts.setHub(hub);
    if (null != hub.getCmtsList()) {
      hub.getCmtsList().add(cmts);
    } else {
      List<Cmts> list = new ArrayList<>();
      list.add(cmts);
      hub.setCmtsList(list);
    }

    try {
      cmtsService.save(cmts, getSlotListFromString());
      FacesUtils.addInfoMessage("CMTS cadastrado com sucesso!");
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
      list();
      this.cmts = new Cmts();
    } catch (Exception e) {
      FacesUtils.addInfoMessage("Não foi possível cadastrar o CMTS! \n" + e.getMessage());
    }
  }

  public void delete() {
    try {
      cmtsService.delete(cmts);
      FacesUtils.addInfoMessage("CMTS removido com sucesso!");
      list();
    } catch (Exception e) {
      FacesUtils.addInfoMessage("Não foi possível remover o CMTS! \n" + e.getMessage());
    }
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
   * @return the slotList
   */
  public String getSlotList() {
    return slotList;
  }

  /**
   * @param slotList the slotList to set
   */
  public void setSlotList(String slotList) {
    this.slotList = slotList;
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
