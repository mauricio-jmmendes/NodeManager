package com.nodemanager.managedbean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;

import com.nodemanager.managedbean.util.FacesUtils;
import com.nodemanager.model.Cmts;
import com.nodemanager.model.Conector;
import com.nodemanager.model.Downstream;
import com.nodemanager.model.Hub;
import com.nodemanager.model.Login;
import com.nodemanager.model.Node;
import com.nodemanager.model.Placa;
import com.nodemanager.model.Projeto;
import com.nodemanager.model.Upstream;
import com.nodemanager.service.NodeService;
import com.nodemanager.service.PlacaService;
import com.nodemanager.service.ProjetoService;
import com.nodemanager.util.JPAUtil;
import com.nodemanager.util.Status;
import com.nodemanager.util.StatusProjeto;

@ManagedBean
@ViewScoped
public class ProjetoMB {

  private static final String DIRETO = "DIRETO";
  private static final String RETORNO = "RETORNO";

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

  private String selectedRetorno;
  private String selectedDireto;

  private String codNodeToBeBroken;
  private String sufix;

  private Upstream up;
  private Downstream down;

  private int qtdRetorno;
  private int qtdDireto;

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
  private List<Node> nodeList;

  private Long projetoId;
  private Projeto projeto;
  private List<Projeto> projetos;
  private ProjetoService projetoService;

  private Node nodeDe;
  private Node nodePara;

  @PostConstruct
  public void init() {

    nodeService = new NodeService(JPAUtil.getSimpleEntityManager());
    node = new Node();
    selectedNodes = new ArrayList<>();

    projetoService = new ProjetoService(JPAUtil.getSimpleEntityManager());

    projeto = new Projeto();
    projetos = new ArrayList<>();

    nodes = new ArrayList<>();
    nodeList = new ArrayList<>();

    placaService = new PlacaService(JPAUtil.getSimpleEntityManager());
    placa = new Placa();
    placas = new ArrayList<>();

    selectedUps = new ArrayList<>();
    selectedDowns = new ArrayList<>();

    selectedConnectors = new ArrayList<>();

    firstPanel = true;
    secondPanel = false;
    displayPanel = false;

    System.gc();

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

    System.gc();

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

    System.gc();

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

    System.gc();

    return "";
  }

  public void loadNode() {

    selectedUps.clear();
    selectedDowns.clear();

    selectedConnectors.clear();
    selectedNodes.clear();

    nodes = nodeService.findByCodNode(codNode);

    projeto.setNodeOrigem(codNode);

    secondPanel = false;

    System.gc();
  }

  public void loadPlacas() {

    placas = placaService.findPlacaByDownstreamStatus(qtdDownstream * qtdDireto, Status.LIVRE);

    if (!placas.isEmpty()) {

      List<Long> CmtsIds = new ArrayList<>();

      for (Iterator<Placa> iterator = placas.iterator(); iterator.hasNext();) {
        Placa myPlaca = (Placa) iterator.next();

        Long id = myPlaca.getSlot().getCmts().getId();

        if (!CmtsIds.contains(id)) {
          CmtsIds.add(id);
        }
      }

      List<Placa> list =
          placaService.findPlacaByUpstreamStatus(CmtsIds, qtdUpstream * qtdRetorno, Status.LIVRE);

      placas.addAll(list);

      Collections.sort(placas, new Comparator<Placa>() {
        @Override
        public int compare(Placa placa, Placa anotherPlaca) {
          return placa.getSlot().getCmts().getNome()
              .compareTo(anotherPlaca.getSlot().getCmts().getNome());
        }
      });

    }

    displayPanel = true;

    firstPanel = false;
    secondPanel = false;

    System.gc();

  }

  public void update() {

    try {
      projetoService.update(projeto);
      FacesUtils.addInfoMessage("Projeto atualizado com sucesso!");
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    } catch (SQLException e) {
      FacesUtils.addInfoMessage("Erro ao atualizar projeto!\n" + e.getMessage());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    }

  }

  public void loadProjetoById() {

    projeto = projetoService.getById(projetoId);

  }

  public void loadProjetos() {

    projetos = projetoService.getByNode(codNode);

  }

  public void deleteProjeto() {


    nodeList = nodeService.findByCodNode(projeto.getNodeDestino());

    for (Node myNode : nodeList) {
      try {
        nodeService.delete(myNode);
        FacesUtils.addInfoMessage("Node destino removindo com sucesso!");
        FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
      } catch (Exception e) {
        FacesUtils.addInfoMessage("Erro ao remover o Node destino!");
        FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
      }
    }

    try {
      projetoService.delete(projeto);
      FacesUtils.addInfoMessage("Projeto excluído com sucesso!");
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    } catch (Exception e) {
      FacesUtils.addInfoMessage("Erro ao excluir o projeto!");
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    }

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

    System.gc();

    // Aqui se tiver mais de um node na lista lançar um dialog perguntando qual node escolher.
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

    System.gc();

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

    System.gc();

    return conectors;
  }

  public List<Upstream> retornoList() {

    List<Upstream> ups = new ArrayList<>();
    if (null != placa.getConectorList()) {
      for (Conector conector : placa.getConectorList()) {
        ups.addAll(conector.getUpstreamList());
      }
    }

    System.gc();

    return ups;
  }

  public List<Downstream> diretoList() {

    List<Downstream> downs = new ArrayList<>();

    if (null != placa.getConectorList()) {
      for (Conector conector : placa.getConectorList()) {
        downs.addAll(conector.getDownstreamList());
      }
    }

    System.gc();

    return downs;
  }

  public List<Upstream> retornoNodesQuebra(String type) {

    List<Upstream> ups = new ArrayList<>();

    List<Node> nodeList;

    if (type.equals("ORIGEM")) {
      nodeList = nodeService.findByCodNode(projeto.getNodeOrigem());
    } else {
      nodeList = nodeService.findByCodNode(projeto.getNodeDestino());
    }

    for (Node node : nodeList) {
      if (null != node.getUpstreams()) {
        for (Upstream upstream : node.getUpstreams()) {
          if (!ups.contains(upstream)) {
            ups.add(upstream);
          }
        }
      }
    }

    System.gc();

    return ups;
  }

  public List<Downstream> diretoNodesQuebra(String type) {

    List<Downstream> downs = new ArrayList<>();

    if (type.equals("ORIGEM")) {
      nodeList = nodeService.findByCodNode(projeto.getNodeOrigem());
    } else {
      nodeList = nodeService.findByCodNode(projeto.getNodeDestino());
    }

    for (Node node : nodeList) {
      if (null != node.getDownstreams()) {
        for (Downstream down : node.getDownstreams()) {
          if (!downs.contains(node)) {
            downs.add(down);
          }
        }
      }
    }

    System.gc();

    return downs;
  }

  private Node getNodeIfAlreadyExists(String codNode, String type) {

    List<Node> nodeList = nodeService.findByCodNode(codNode);

    for (Node myNode : nodeList) {
      if (myNode.getType().equals(type)) {
        return myNode;
      }
    }

    return new Node();

  }

  public StatusProjeto[] getStatusValues() {
    return StatusProjeto.values();
  }

  public void onCellEdit(Upstream upstream) {

    String codRetorno = codNodeToBeBroken + "-" + sufix;

    Node myNode = getNodeIfAlreadyExists(codRetorno, RETORNO);

    for (Node retorno : nodeList) {
      if (retorno.getCodNode().equalsIgnoreCase(codRetorno) && retorno.getType().equals(RETORNO)) {
        myNode = retorno;
      }
    }

    if ((upstream.getNodes() == null) || (!upstream.getNodes().contains(myNode))) {

      if ((myNode.getUpstreams() == null) || (myNode.getUpstreams().isEmpty())) {
        myNode.setCodNode(codRetorno);

        myNode.setType(RETORNO);
        myNode.setStatusNode(Status.EM_PROJETO);

        upstream.getNodes().add(myNode);
        upstream.setStatusUp(Status.OCUPADO);

        List<Upstream> ups = new ArrayList<>();

        ups.add(upstream);

        myNode.setUpstreams(ups);
      } else {
        upstream.getNodes().add(myNode);
        upstream.setStatusUp(Status.OCUPADO);

        List<Upstream> ups = new ArrayList<>();

        ups.add(upstream);
        myNode.getUpstreams().addAll(ups);
      }

      FacesUtils.addInfoMessage("Retorno " + codRetorno + " adicionado na up "
          + upstream.getNumUpStream());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);

      if (!nodeList.contains(myNode)) {
        nodeList.add(myNode);
      }

    } else {
      FacesUtils.addInfoMessage("O Retorno " + codRetorno + " já existe nessa up! "
          + upstream.getNumUpStream());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    }

    sufix = "";

    System.gc();

  }

  public void onCellEdit(Downstream downstream) {

    String codDireto = codNodeToBeBroken + "-" + sufix;

    Node myNode = getNodeIfAlreadyExists(codDireto, RETORNO);

    for (Node direto : nodeList) {
      if (direto.getCodNode().equalsIgnoreCase(codDireto)
          && direto.getType().equalsIgnoreCase(DIRETO)) {
        myNode = direto;
      }
    }

    if ((downstream.getNodes() == null) || (!downstream.getNodes().contains(myNode))) {

      if ((myNode.getDownstreams() == null) || (myNode.getDownstreams().isEmpty())) {
        myNode.setCodNode(codDireto);

        myNode.setType(DIRETO);
        myNode.setStatusNode(Status.EM_PROJETO);

        downstream.getNodes().add(myNode);
        downstream.setStatusDown(Status.OCUPADO);

        List<Downstream> downs = new ArrayList<>();

        downs.add(downstream);

        myNode.setDownstreams(downs);

      } else {
        downstream.getNodes().add(myNode);
        downstream.setStatusDown(Status.OCUPADO);

        List<Downstream> downs = new ArrayList<>();

        downs.add(downstream);
        myNode.getDownstreams().addAll(downs);
      }

      FacesUtils.addInfoMessage("O Direto " + codDireto + " adicionado na down "
          + downstream.getNumDownStream());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);

      if (!nodeList.contains(myNode)) {
        nodeList.add(myNode);
      }

    } else {
      FacesUtils.addInfoMessage("O Retorno " + codDireto + " já existe nessa down! "
          + downstream.getNumDownStream());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    }

    sufix = "";

    System.gc();

  }

  public void removeNode(Upstream up) {

    List<Node> myListNodes = up.getNodes();

    for (Iterator<Node> iterator = myListNodes.iterator(); iterator.hasNext();) {
      Node myNode = iterator.next();

      if (myNode.getCodNode().contains(codNodeToBeBroken)) {
        iterator.remove();
        myNode.getUpstreams().remove(up);

        nodeList.add(myNode);

        // se é um node novo e não está em nenhuma outra up, então remover da lista
        if (myNode.getUpstreams().isEmpty() && myNode.getId() == null) {
          nodeList.remove(myNode);
        }
      }

      if (myListNodes.isEmpty()) {
        up.setStatusUp(Status.LIVRE);
      }
    }

    System.gc();
  }

  public void removeNode(Downstream down) {

    List<Node> myListNodes = down.getNodes();

    for (Iterator<Node> iterator = myListNodes.iterator(); iterator.hasNext();) {
      Node myNode = iterator.next();

      if (myNode.getCodNode().contains(codNodeToBeBroken)) {
        iterator.remove();
        myNode.getDownstreams().remove(down);

        nodeList.add(myNode);

        // se é um node novo e não está em nenhuma down, então remover da lista
        if (myNode.getDownstreams().isEmpty() && myNode.getId() == null) {
          nodeList.remove(myNode);
        }
      }

      if (myListNodes.isEmpty()) {
        down.setStatusDown(Status.LIVRE);
      }
    }

    System.gc();
  }


  public void reset() {

    loadNode();
    firstPanel = true;
    secondPanel = false;
    displayPanel = false;

  }

  public void save() {
    for (Node myNode : nodeList) {
      if (myNode.getId() == null) {
        try {

          nodeService.save(myNode);
          FacesUtils.addInfoMessage(StringUtils.capitalize(myNode.getType().toLowerCase()) + " "
              + myNode.getCodNode() + " cadastrado com sucesso!");
          FacesUtils.getExternalContext().getFlash().setKeepMessages(true);

        } catch (Exception e) {

          FacesUtils.addInfoMessage("Erro ao cadastrar o "
              + StringUtils.capitalize(myNode.getType().toLowerCase()) + " " + myNode.getCodNode()
              + "\n" + e.getMessage());
          FacesUtils.getExternalContext().getFlash().setKeepMessages(true);

        }
      } else if (myNode.getDownstreams().isEmpty() && myNode.getUpstreams().isEmpty()) {
        try {

          nodeService.delete(myNode);
          FacesUtils.addInfoMessage(StringUtils.capitalize(myNode.getType().toLowerCase()) + " "
              + myNode.getCodNode() + " removido com sucesso!");
          FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception e) {
          FacesUtils.addInfoMessage("Erro ao remover o "
              + StringUtils.capitalize(myNode.getType().toLowerCase()) + " " + myNode.getCodNode()
              + "\n" + e.getMessage());
          FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
        }

      } else {
        try {
          nodeService.update(myNode);
          FacesUtils.addInfoMessage(StringUtils.capitalize(myNode.getType().toLowerCase()) + " "
              + myNode.getCodNode() + " atualizado com sucesso!");
          FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception e) {
          FacesUtils.addInfoMessage("Erro ao atualizar o "
              + StringUtils.capitalize(myNode.getType().toLowerCase()) + " " + myNode.getCodNode()
              + "\n" + e.getMessage());
          FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
        }
      }
    }

    Login login = new Login();

    login.setLogin("admin");
    login.setSenha("1234");

    projeto.setNodeDestino(codNodeToBeBroken);
    projeto.setDataProjeto(Calendar.getInstance().getTime());
    projeto.setDescricao("Quebra de Node");
    projeto.setStatusProjeto(StatusProjeto.EM_ANDAMENTO);
    projeto.setUsuario(login);
    projeto.setObs("Node será quebrado sem exclusão do Antigo.");

    try {
      projetoService.save(projeto);
      FacesUtils.addInfoMessage("Projeto salvo com Sucesso!");
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    } catch (Exception e) {
      FacesUtils.addInfoMessage("Erro ao atualizar o Projeto" + e.getMessage());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    }

    System.gc();

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
    this.codNode = codNode.toUpperCase();
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
   * @return the codNodeToBeBroken
   */
  public String getCodNodeToBeBroken() {
    return codNodeToBeBroken;
  }

  /**
   * @param codNodeToBeBroken the codNodeToBeBroken to set
   */
  public void setCodNodeToBeBroken(String codNodeToBeBroken) {
    this.codNodeToBeBroken = codNodeToBeBroken.toUpperCase();
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
   * @return the qtdDireto
   */
  public int getQtdDireto() {
    return qtdDireto;
  }

  /**
   * @param qtdDireto the qtdDireto to set
   */
  public void setQtdDireto(int qtdDireto) {
    this.qtdDireto = qtdDireto;
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

  /**
   * @return the projeto
   */
  public Projeto getProjeto() {
    return projeto;
  }

  /**
   * @param projeto the projeto to set
   */
  public void setProjeto(Projeto projeto) {
    this.projeto = projeto;
  }

  /**
   * @return the projetos
   */
  public List<Projeto> getProjetos() {
    return projetos;
  }

  /**
   * @param projetos the projetos to set
   */
  public void setProjetos(List<Projeto> projetos) {
    this.projetos = projetos;
  }

  /**
   * @return the sufix
   */
  public String getSufix() {
    return sufix;
  }

  /**
   * @param sufix the sufix to set
   */
  public void setSufix(String sufix) {
    this.sufix = sufix.toUpperCase();
  }

  /**
   * @return the projetoId
   */
  public Long getProjetoId() {
    return projetoId;
  }

  /**
   * @param projetoId the projetoId to set
   */
  public void setProjetoId(Long projetoId) {
    this.projetoId = projetoId;
  }

  /**
   * @return the nodeDe
   */
  public Node getNodeDe() {
    return nodeDe;
  }

  /**
   * @param nodeDe the nodeDe to set
   */
  public void setNodeDe(Node nodeDe) {
    this.nodeDe = nodeDe;
  }

  /**
   * @return the nodePara
   */
  public Node getNodePara() {
    return nodePara;
  }

  /**
   * @param nodePara the nodePara to set
   */
  public void setNodePara(Node nodePara) {
    this.nodePara = nodePara;
  }

}
