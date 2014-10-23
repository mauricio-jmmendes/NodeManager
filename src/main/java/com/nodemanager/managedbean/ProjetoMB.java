package com.nodemanager.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Cmts;
import com.nodemanager.model.Conector;
import com.nodemanager.model.Downstream;
import com.nodemanager.model.Hub;
import com.nodemanager.model.Node;
import com.nodemanager.model.Placa;
import com.nodemanager.model.Upstream;
import com.nodemanager.service.NodeService;
import com.nodemanager.service.PlacaService;
import com.nodemanager.util.JPAUtil;
import com.nodemanager.util.Status;

@ManagedBean
@ViewScoped
public class ProjetoMB {

  private String codNode;
  private Node node;
  private List<Node> nodes;
  private List<Node> selectedNodes;
  private NodeService nodeService;

  private Long cmtsId;
  private List<Cmts> cmtsList;

  private Long placaId;
  private Placa placa;

  private List<Placa> placas;
  private PlacaService placaService;

  private List<Upstream> selectedUps;
  private List<Downstream> selectedDowns;

  private List<String> selectedCheckBoxesUps;
  private List<String> selectedCheckBoxesDowns;

  private String selectedRetorno;
  private String selectedDireto;

  private String strNodeQuebrado;

  private Upstream up;
  private Downstream down;

  private int qtdRetorno;
  private Long qtdUpstream;
  private Long qtdDownstream;

  private boolean soUpsLivres;
  private boolean soDownsLivres;

  private Hub hub;

  private Conector conn;

  private boolean firstPanel;
  private boolean secondPanel;

  private boolean displayPanel;
  private boolean cmtsChecked;

  private List<Conector> selectedConnectors;

  @PostConstruct
  public void init() {

    nodeService = new NodeService(JPAUtil.getSimpleEntityManager());
    node = new Node();
    selectedNodes = new ArrayList<>();

    nodes = new ArrayList<>();

    placaService = new PlacaService(JPAUtil.getSimpleEntityManager());
    placa = new Placa();
    placas = new ArrayList<>();

    selectedUps = new ArrayList<>();
    selectedDowns = new ArrayList<>();

    selectedConnectors = new ArrayList<>();

    firstPanel = true;
    secondPanel = false;
    displayPanel = false;

  }

  public String setUpstreamRendered(Upstream up, Node myNode) {

    if (null != up && null != myNode) {
      if (myNode.getCodNode().contains(codNode)) {
        if (!selectedUps.contains(up)) {
          selectedUps.add(up);
          return String.valueOf(up.getNumUpStream());
        }
      }
    }

    return "";
  }

  public String setDownstreamRendered(Downstream down, Node myNode) {

    if (null != down && null != myNode) {
      if (myNode.getCodNode().contains(codNode)) {
        if (!selectedDowns.contains(down)) {
          selectedDowns.add(down);
          return String.valueOf(down.getNumDownStream());
        }
      }
    }

    return "";
  }

  public String setNodeRendered(Conector conn, Node myNode) {

    if (!selectedConnectors.contains(conn)) {
      selectedNodes.clear();
      selectedConnectors.add(conn);
    }

    if (null != conn && null != myNode) {
      if (myNode.getCodNode().contains(codNode)) {
        if (!selectedNodes.contains(myNode)) {
          selectedNodes.add(myNode);
          return String.valueOf(myNode.getCodNode());
        }
      }
    }

    return "";
  }

  public void loadNode() {

    selectedUps.clear();
    selectedDowns.clear();

    selectedConnectors.clear();
    selectedNodes.clear();

    nodes = nodeService.findByCodNode(codNode);

    secondPanel = false;
  }

  public void loadPlacas() {

    placas = placaService.findPlacaByUpstreamStatus(cmtsId, qtdUpstream, Status.LIVRE);

    displayPanel = true;

    firstPanel = false;
    secondPanel = false;

  }

  public void prepareNodeForQuebra() {
    node = getNodeFromConector();

    selectedConnectors.clear();
    selectedUps.clear();

    hub = getCurrentHub();
    cmtsList = hub.getCmtsList();

    secondPanel = true;
  }

  private Hub getCurrentHub() {
    return conn.getPlaca().getSlot().getCmts().getHub();
  }

  private Node getNodeFromConector() {

    nodes.clear();

    if (null != conn.getUpstreamList()) {
      for (Upstream myUp : conn.getUpstreamList()) {
        for (Node myNode : myUp.getNodes()) {
          if (myNode.getCodNode().contains(codNode)) {
            nodes.add(myNode);
          }
        }
      }
    } else {
      for (Downstream myDown : conn.getDownstreamList()) {
        for (Node myNode : myDown.getNodes()) {
          if (myNode.getCodNode().contains(codNode)) {
            nodes.add(myNode);
          }
        }
      }
    }
    return nodes.get(0);
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
      if (node.getCodNode().contains(codNode)) {
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
      if (node.getCodNode().contains(codNode)) {
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

  public List<Upstream> retornoList() {

    List<Upstream> ups = new ArrayList<>();
    if (null != placa.getConectorList()) {
      for (Conector conector : placa.getConectorList()) {
        ups.addAll(conector.getUpstreamList());
      }
    }
    return ups;
  }

  public List<Downstream> diretoList() {

    List<Downstream> downs = new ArrayList<>();

    if (null != placa.getConectorList()) {
      for (Conector conector : placa.getConectorList()) {
        downs.addAll(conector.getDownstreamList());
      }
    }
    return downs;
  }

  public void onCellEdit(Upstream upstream) {
    Node myNode = new Node();
    myNode.setCodNode(strNodeQuebrado);

    upstream.getNodes().add(myNode);

    List<Upstream> ups = new ArrayList<>();

    ups.add(upstream);

    myNode.setUpstreams(ups);

    try {
      nodeService.save(myNode);
      FacesUtils.addInfoMessage("Retorno " + myNode.getCodNode() + "criado com sucesso!");
    } catch (Exception e) {
      FacesUtils.addInfoMessage("Não foi possível criar o Retorno " + myNode.getCodNode() + "! \n"
          + e.getMessage());
    }


    strNodeQuebrado = "";

  }

  public void reset() {

    loadNode();
    firstPanel = true;
    secondPanel = false;
    displayPanel = false;

  }

  public void onCellEdit(Downstream downstream) {
    Node node = new Node();
    node.setCodNode(strNodeQuebrado);

    downstream.getNodes().add(node);

    List<Downstream> downs = new ArrayList<>();

    downs.add(downstream);

    node.setDownstreams(downs);

    nodeService.save(node);

    strNodeQuebrado = "";

  }

  public void save() {
    for (Node node : nodes) {
      nodeService.save(node);
    }
  }

  /**
   * @return the codNode
   */
  public String getCodNode() {
    return codNode;
  }

  /**
   * @param codNode the codNode to set
   */
  public void setCodNode(String codNode) {
    this.codNode = codNode;
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
   * @return the selectedNodes
   */
  public List<Node> getSelectedNodes() {
    return selectedNodes;
  }

  /**
   * @param selectedNodes the selectedNodes to set
   */
  public void setSelectedNodes(List<Node> selectedNodes) {
    this.selectedNodes = selectedNodes;
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
   * @return the selectedCheckBoxesUps
   */
  public List<String> getSelectedCheckBoxesUps() {
    return selectedCheckBoxesUps;
  }

  /**
   * @param selectedCheckBoxesUps the selectedCheckBoxesUps to set
   */
  public void setSelectedCheckBoxesUps(List<String> selectedCheckBoxesUps) {
    this.selectedCheckBoxesUps = selectedCheckBoxesUps;
  }

  /**
   * @return the selectedCheckBoxesDowns
   */
  public List<String> getSelectedCheckBoxesDowns() {
    return selectedCheckBoxesDowns;
  }

  /**
   * @param selectedCheckBoxesDowns the selectedCheckBoxesDowns to set
   */
  public void setSelectedCheckBoxesDowns(List<String> selectedCheckBoxesDowns) {
    this.selectedCheckBoxesDowns = selectedCheckBoxesDowns;
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
   * @return the strNodeQuebrado
   */
  public String getStrNodeQuebrado() {
    return strNodeQuebrado;
  }

  /**
   * @param strNodeQuebrado the strNodeQuebrado to set
   */
  public void setStrNodeQuebrado(String strNodeQuebrado) {
    this.strNodeQuebrado = strNodeQuebrado;
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
   * @return the qtdRetorno
   */
  public int getQtdRetorno() {
    return qtdRetorno;
  }

  /**
   * @param qtdRetorno the qtdRetorno to set
   */
  public void setQtdRetorno(int qtdRetorno) {
    this.qtdRetorno = qtdRetorno;
  }

  /**
   * @return the qtdUpstream
   */
  public Long getQtdUpstream() {
    return qtdUpstream;
  }

  /**
   * @param qtdUpstream the qtdUpstream to set
   */
  public void setQtdUpstream(Long qtdUpstream) {
    this.qtdUpstream = qtdUpstream;
  }

  /**
   * @return the qtdDownstream
   */
  public Long getQtdDownstream() {
    return qtdDownstream;
  }

  /**
   * @param qtdDownstream the qtdDownstream to set
   */
  public void setQtdDownstream(Long qtdDownstream) {
    this.qtdDownstream = qtdDownstream;
  }

  /**
   * @return the soUpsLivres
   */
  public boolean isSoUpsLivres() {
    return soUpsLivres;
  }

  /**
   * @param soUpsLivres the soUpsLivres to set
   */
  public void setSoUpsLivres(boolean soUpsLivres) {
    this.soUpsLivres = soUpsLivres;
  }

  /**
   * @return the soDownsLivres
   */
  public boolean isSoDownsLivres() {
    return soDownsLivres;
  }

  /**
   * @param soDownsLivres the soDownsLivres to set
   */
  public void setSoDownsLivres(boolean soDownsLivres) {
    this.soDownsLivres = soDownsLivres;
  }

  /**
   * @return the conn
   */
  public Conector getConn() {
    return conn;
  }

  /**
   * @param conn the conn to set
   */
  public void setConn(Conector conn) {
    this.conn = conn;
  }

  /**
   * @return the show
   */
  public boolean isSecondPanel() {
    return secondPanel;
  }

  /**
   * @param show the show to set
   */
  public void setSecondPanel(boolean secondPanel) {
    this.secondPanel = secondPanel;
  }

  /**
   * @return the firstPanel
   */
  public boolean isFirstPanel() {
    return firstPanel;
  }

  /**
   * @param firstPanel the firstPanel to set
   */
  public void setFirstPanel(boolean firstPanel) {
    this.firstPanel = firstPanel;
  }

  /**
   * @return the displayPanel
   */
  public boolean isDisplayPanel() {
    return displayPanel;
  }

  /**
   * @param displayPanel the displayPanel to set
   */
  public void setDisplayPanel(boolean displayPanel) {
    this.displayPanel = displayPanel;
  }

  /**
   * @return the cmtsChecked
   */
  public boolean isCmtsChecked() {
    return cmtsChecked;
  }

  /**
   * @param cmtsChecked the cmtsChecked to set
   */
  public void setCmtsChecked(boolean cmtsChecked) {
    this.cmtsChecked = cmtsChecked;
  }
}
