package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

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
public class ProjetoMB {

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

  private List<Upstream> selectedUps;
  private List<Downstream> selectedDowns;

  private String selectedRetorno;
  private String selectedDireto;

  private Upstream up;
  private Downstream down;

  private String style;

  @PostConstruct
  public void init() {

    nodeService = new NodeService(JPAUtil.getSimpleEntityManager());
    node = new Node();

    nodes = new ArrayList<>();

    hubService = new HubService(JPAUtil.getSimpleEntityManager());
    hubs = hubService.findAll();

    placa = new Placa();
    placas = new ArrayList<>();

    selectedUps = new ArrayList<>();
    selectedDowns = new ArrayList<>();


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

  public String getRowSpanRetorno() {
    int rowSpan = 0;
    int a = 0, b = 0, c = 0, d = 0;

    for (Conector conn : retornoConnectorsList()) {
      for (Upstream retorno : conn.getUpstreamList()) {
        for (Node myNode : retorno.getNodes()) {
          if (myNode.getCodNode().contains(this.node.getCodNode())) {
            if (myNode.getCodNode().endsWith("A")) {
              a = a + 1;
            } else if (myNode.getCodNode().endsWith("B")) {
              b = b + 1;
            } else if (myNode.getCodNode().endsWith("C")) {
              c = c + 1;
            } else if (myNode.getCodNode().endsWith("D")) {
              d = d + 1;
            }
          }
        }
      }
    }

    if ((a > b) && (a > c) && (a > d)) {
      rowSpan = a;
    } else if ((b > c) && (b > d)) {
      rowSpan = b;
    } else if ((c > d)) {
      rowSpan = c;
    } else {
      rowSpan = d;
    }

    return String.valueOf(rowSpan);

  }

  public String setUpstreamRendered(Upstream up, Node myNode) {

    if (null != up && null != myNode) {
      if (myNode.getCodNode().contains(this.node.getCodNode())) {
        if (!selectedUps.contains(up)) {
          selectedUps.add(up);
          return String.valueOf(up.getNumUpStream());
        }
      }
    }

    return "";
  }

  public Boolean isDownstreamRendered(Downstream down) {

    if (selectedDowns.contains(down)) {
      return false;
    }

    selectedDowns.add(down);
    return true;
  }

  public void loadNode() {

    selectedUps = new ArrayList<>();
    selectedDowns = new ArrayList<>();

    nodes = nodeService.findByCodNode(node.getCodNode());
  }

  public void viewDialogQuebra() {
    Map<String, Object> options = new HashMap<String, Object>();
    options.put("modal", true);
    options.put("draggable", false);
    options.put("resizable", false);
    options.put("contentHeight", 420);

    RequestContext.getCurrentInstance().openDialog("dlgQuebra", options, null);
  }


  public void loadPlaca() {
    for (Placa myPlaca : placas) {
      if (myPlaca.getId() == placaId) {
        placa = myPlaca;
      }
    }
  }


  public List<Conector> retornoConnectorsList() {

    List<Conector> conectors = new ArrayList<>();

    for (Node node : nodes) {
      if (node.getCodNode().contains(this.node.getCodNode())) {
        if (null != node.getUpstreams()) {
          for (Upstream up : node.getUpstreams()) {
            if (!conectors.contains(up.getConectorUp())) {
              conectors.add(up.getConectorUp());
            }
          }
        }
      }
    }

    return conectors;
  }

  public List<Conector> diretoConnectorsList() {

    List<Conector> conectors = new ArrayList<>();

    for (Node node : nodes) {
      if (node.getCodNode().contains(this.node.getCodNode())) {
        if (null != node.getDownstreams()) {
          for (Downstream down : node.getDownstreams()) {
            if (!conectors.contains(down.getConectorDown())) {
              conectors.add(down.getConectorDown());
            }
          }
        }
      }
    }

    return conectors;
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
  public List<Upstream> getSelectedUps() {
    return selectedUps;
  }

  /**
   * @param selectedUps the selectedUps to set
   */
  public void setSelectedUps(List<Upstream> selectedUps) {
    if (null == this.selectedUps) {
      this.selectedUps = selectedUps;
    } else if (selectedUps.size() != 0) {
      this.selectedUps.addAll(selectedUps);
    }
  }

  /**
   * @return the selectedDowns
   */
  public List<Downstream> getSelectedDowns() {
    return selectedDowns;
  }

  /**
   * @param selectedDowns the selectedDowns to set
   */
  public void setSelectedDowns(List<Downstream> selectedDowns) {
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

  /**
   * @return the up
   */
  public Upstream getUp() {
    return up;
  }

  /**
   * @param up the up to set
   */
  public void setUp(Upstream up) {
    this.up = up;
  }

  /**
   * @return the down
   */
  public Downstream getDown() {
    return down;
  }

  /**
   * @param down the down to set
   */
  public void setDown(Downstream down) {
    this.down = down;
  }

  /**
   * @return the style
   */
  public String getStyle() {
    return style;
  }

  /**
   * @param style the style to set
   */
  public void setStyle(String style) {
    this.style = style;
  }
}
