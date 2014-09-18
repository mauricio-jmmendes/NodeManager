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

  private Node node;
  private List<Node> nodes;
  private NodeService nodeService;

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

  private String selectedRetorno;
  private String selectedDireto;

  @PostConstruct
  public void init() {

    nodeService = new NodeService(JPAUtil.getSimpleEntityManager());
    node = new Node();

    nodes = new ArrayList<>();

    hubService = new HubService(JPAUtil.getSimpleEntityManager());
    hubs = hubService.findAll();

    placa = new Placa();
    placas = new ArrayList<>();

  }

  public void prepareToAdd() {
    this.node = new Node();
    this.hubs = hubService.findAll();

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

  public void loadNode() {
    nodes = nodeService.findByCodNode(node.getCodNode());
  }


  public void loadPlaca() {
    for (Placa myPlaca : placas) {
      if (myPlaca.getId() == placaId) {
        placa = myPlaca;
      }
    }
  }

  public void addUps() {

    String retorno = node.getCodNode() + "-" + selectedRetorno;

    node.setCodNode(retorno);

    List<Upstream> retornos = new ArrayList<>();

    for (String idUp : selectedUps) {
      for (Conector conn : placa.getConectorList()) {
        
        if (null == conn.getUpstreamList()) {
          continue;
        }
        
        for (Upstream up : conn.getUpstreamList()) {
          if (up.getId() == Long.parseLong(idUp)) {
            if (null != up.getNodes()) {
              if (!up.getNodes().contains(node)) {
                up.getNodes().add(node);
                retornos.add(up);
                break;
              }
            } else {
              List<Node> nodes = new ArrayList<>();
              nodes.add(node);

              up.setNodes(nodes);

              retornos.add(up);
              break;
            }
          }
        }
      }
    }



    if (!retornos.isEmpty()) {
      if (null != node.getUpstreams()) {
        node.getUpstreams().addAll(retornos);
      } else {
        node.setUpstreams(retornos);
      }

      selectedUps.clear();
    }

    if (!nodes.contains(node)) {
      nodes.add(node);
      node = new Node();
    }
  }

  public List<Upstream> retornoList() {

    List<Upstream> ups = new ArrayList<>();

    for (Node node : nodes) {
      if (null != node.getUpstreams()) {
        for (Upstream upstream : node.getUpstreams()) {
          if (!ups.contains(upstream)) {
            ups.add(upstream);
          }
        }
      }
    }

    return ups;
  }

  public List<Downstream> diretoList() {
    List<Downstream> downs = new ArrayList<>();
    for (Node node : nodes) {
      if (null != node.getDownstreams()) {
        for (Downstream down : node.getDownstreams()) {
          if (!downs.contains(node)) {
            downs.add(down);
          }
        }
      }
    }

    return downs;
  }


  public void addDowns() {

    String retorno = node.getCodNode() + "-" + selectedDireto;

    node.setCodNode(retorno);

    List<Downstream> diretos = new ArrayList<>();

    for (String idDown : selectedDowns) {
      for (Conector conn : placa.getConectorList()) {
        if (null == conn.getDownstreamList()) {
          continue;
        }
        for (Downstream down : conn.getDownstreamList()) {
          if (down.getId() == Long.parseLong(idDown)) {
            if (null != down.getNodes()) {
              if (!down.getNodes().contains(node)) {
                down.getNodes().add(node);
                diretos.add(down);
                break;
              }
            } else {
              List<Node> nodes = new ArrayList<>();
              nodes.add(node);

              down.setNodes(nodes);

              diretos.add(down);
              break;
            }
            
            
            
            if (!down.getNodes().contains(node)) {
              down.getNodes().add(node);
              diretos.add(down);
              break;
            }
          }
        }
      }
    }

    if (!diretos.isEmpty()) {
      if (null != node.getUpstreams()) {
        node.getDownstreams().addAll(diretos);
      } else {
        node.setDownstreams(diretos);
      }

      selectedDowns.clear();
    }

    if (!nodes.contains(node)) {
      nodes.add(node);
      node = new Node();
    }
  }

  public void save() {
    for (Node node : nodes) {
      nodeService.save(node);
    }
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
   * @return the nodes
   */
  public List<Node> getNodes() {
    return nodes;
  }

  /**
   * @param nodes the nodes to set
   */
  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
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

  /**
   * @return the selectedRetorno
   */
  public String getSelectedRetorno() {
    return selectedRetorno;
  }

  /**
   * @param selectedRetorno the selectedRetorno to set
   */
  public void setSelectedRetorno(String selectedRetorno) {
    this.selectedRetorno = selectedRetorno;
  }

  /**
   * @return the selectedDireto
   */
  public String getSelectedDireto() {
    return selectedDireto;
  }

  /**
   * @param selectedDireto the selectedDireto to set
   */
  public void setSelectedDireto(String selectedDireto) {
    this.selectedDireto = selectedDireto;
  }

}
