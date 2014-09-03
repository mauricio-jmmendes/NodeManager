package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nodemanager.model.Cmts;
import com.nodemanager.model.Conector;
import com.nodemanager.model.Downstream;
import com.nodemanager.model.Hub;
import com.nodemanager.model.Node;
import com.nodemanager.model.Placa;
import com.nodemanager.model.Slot;
import com.nodemanager.model.Upstream;
import com.nodemanager.service.HubService;
import com.nodemanager.service.NodeService;
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

  private List<String> selectedUps;
  private List<String> selectedDowns;

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
    for (Placa myPlaca : placas) {
      if (myPlaca.getId() == placaId) {
        placa = myPlaca;
      }
    }
  }

  public void save() {
    List<Upstream> ups = new ArrayList<>();
    List<Downstream> downs = new ArrayList<>();

    for (String idUp : selectedUps) {
      for (Conector conn : placa.getConectorList()) {
        for (Upstream up : conn.getUpstreamList()) {
          if (up.getId() == Long.parseLong(idUp)) {
            up.getNodes().add(node);
            ups.add(up);
            break;
          }
        }
      }
    }

    for (String idDown : selectedDowns) {
      for (Conector conn : placa.getConectorList()) {
        for (Downstream down : conn.getDownstreamList()) {
          if (down.getId() == Long.parseLong(idDown)) {
            down.getNodes().add(node);
            downs.add(down);
            break;
          }
        }
      }
    }

    node.setUpstreams(ups);
    node.setDownstreams(downs);

    nodeService.save(node);

  }

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
  public List<String> getSelectedUps() {
    return selectedUps;
  }

  /**
   * @param selectedUps the selectedUps to set
   */
  public void setSelectedUps(List<String> selectedUps) {
    if (null == this.selectedUps) {
      this.selectedUps = selectedUps;
    } else if (selectedUps.size() != 0) {
      this.selectedUps.addAll(selectedUps);
    }
  }

  /**
   * @return the selectedDowns
   */
  public List<String> getSelectedDowns() {
    return selectedDowns;
  }

  /**
   * @param selectedDowns the selectedDowns to set
   */
  public void setSelectedDowns(List<String> selectedDowns) {
    if (null == this.selectedDowns) {
      this.selectedDowns = selectedDowns;
    } else if (selectedDowns.size() != 0) {
      this.selectedDowns.addAll(selectedDowns);
    }
  }

}
