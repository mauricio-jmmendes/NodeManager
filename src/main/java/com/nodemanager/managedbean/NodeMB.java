package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nodemanager.model.Cmts;
import com.nodemanager.model.Hub;
import com.nodemanager.model.Node;
import com.nodemanager.model.Placa;
import com.nodemanager.model.Slot;
import com.nodemanager.service.HubService;
import com.nodemanager.service.NodeService;
import com.nodemanager.service.PlacaService;
import com.nodemanager.util.JPAUtil;

@ManagedBean
@ViewScoped
public class NodeMB {

  Node node;
  NodeService nodeService;

  private Long hubId;
  private List<Hub> hubs;

  private HubService hubService;

  private Long cmtsId;
  private List<Cmts> cmtsList;

  private Long placaId;
  private Placa placa;
  private List<Placa> placas;
  
  private String[] selectedUps;
  private String[] selectedDowns;

  private PlacaService placaService;

  @PostConstruct
  public void init() {

    nodeService = new NodeService(JPAUtil.getSimpleEntityManager());
    node = new Node();

    hubService = new HubService(JPAUtil.getSimpleEntityManager());
    hubs = hubService.findAll();

    placa = new Placa();
    placas = new ArrayList<>();

  }

  public void handleHubListBoxChange() {
    for (Hub myHub : hubs) {
      if (myHub.getId() == hubId) {
        cmtsList = myHub.getCmtsList();
      }
    }
  }

  public void handleCmtsListBoxChange() {
    placas = new ArrayList<>();

    for (Cmts myCmts : cmtsList) {
      if (myCmts.getId() == cmtsId) {
        for (Slot slot : myCmts.getSlots()) {
          if (null != slot.getPlaca()) {
            placas.add(slot.getPlaca());
          }
        }
        break;
      }
    }
  }
  
  public void loadPlaca() {
    for(Placa myPlaca : placas){
      if (myPlaca.getId() == placaId) {
        placa = myPlaca;
      }
    }
  }

  public void save() {}

  /**
   * @return the node
   */
  public Node getNode() {
    return node;
  }

  /**
   * @param node the node to set
   */
  public void setNode(Node node) {
    this.node = node;
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

  /**
   * @return the hubs
   */
  public List<Hub> getHubs() {
    return hubs;
  }

  /**
   * @param hubs the hubs to set
   */
  public void setHubs(List<Hub> hubs) {
    this.hubs = hubs;
  }

  /**
   * @return the cmtsId
   */
  public Long getCmtsId() {
    return cmtsId;
  }

  /**
   * @param cmtsId the cmtsId to set
   */
  public void setCmtsId(Long cmtsId) {
    this.cmtsId = cmtsId;
  }

  /**
   * @return the cmtsList
   */
  public List<Cmts> getCmtsList() {
    return cmtsList;
  }

  /**
   * @param cmtsList the cmtsList to set
   */
  public void setCmtsList(List<Cmts> cmtsList) {
    this.cmtsList = cmtsList;
  }

  /**
   * @return the placa
   */
  public Placa getPlaca() {
    return placa;
  }

  /**
   * @return the placaId
   */
  public Long getPlacaId() {
    return placaId;
  }

  /**
   * @param placaId the placaId to set
   */
  public void setPlacaId(Long placaId) {
    this.placaId = placaId;
  }

  /**
   * @param placa the placa to set
   */
  public void setPlaca(Placa placa) {
    this.placa = placa;
  }

  /**
   * @return the placas
   */
  public List<Placa> getPlacas() {
    return placas;
  }

  /**
   * @param placas the placas to set
   */
  public void setPlacas(List<Placa> placas) {
    this.placas = placas;
  }

  /**
   * @return the selectedUps
   */
  public String[] getSelectedUps() {
    return selectedUps;
  }

  /**
   * @param selectedUps the selectedUps to set
   */
  public void setSelectedUps(String[] selectedUps) {
    this.selectedUps = selectedUps;
  }

  /**
   * @return the selectedDowns
   */
  public String[] getSelectedDowns() {
    return selectedDowns;
  }

  /**
   * @param selectedDowns the selectedDowns to set
   */
  public void setSelectedDowns(String[] selectedDowns) {
    this.selectedDowns = selectedDowns;
  }

}
