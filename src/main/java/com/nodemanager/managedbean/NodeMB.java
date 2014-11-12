package com.nodemanager.managedbean;

import java.util.ArrayList;
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
import com.nodemanager.model.Node;
import com.nodemanager.model.Placa;
import com.nodemanager.model.Slot;
import com.nodemanager.model.Upstream;
import com.nodemanager.service.HubService;
import com.nodemanager.service.NodeService;
import com.nodemanager.util.JPAUtil;
import com.nodemanager.util.Status;

@ManagedBean
@ViewScoped
public class NodeMB {

  private static final String DIRETO = "DIRETO";

  private static final String RETORNO = "RETORNO";

  private String codNode;

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

  private String inputCodNode;

  private List<Node> nodeList;

  @PostConstruct
  public void init() {

    nodeService = new NodeService(JPAUtil.getSimpleEntityManager());
    node = new Node();

    nodes = new ArrayList<>();
    nodeList = new ArrayList<>();

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

  public void loadNodes() {
    nodes = nodeService.findByCodNode(codNode);
  }

  public void loadPlaca() {
    for (Placa myPlaca : placas) {
      if (myPlaca.getId() == placaId) {
        placa = myPlaca;
      }
    }
  }

  public void onCellEdit(Upstream upstream) {

    String StrCod = codNode + "-" + inputCodNode;

    Node myNode = getNodeIfAlreadyExists(StrCod, RETORNO);

    for (Node retorno : nodeList) {
      if (retorno.getCodNode().equalsIgnoreCase(StrCod) && retorno.getType().equals(RETORNO)) {
        myNode = retorno;
      }
    }

    if ((upstream.getNodes() == null) || (!upstream.getNodes().contains(myNode))) {

      if ((myNode.getUpstreams() == null) || (myNode.getUpstreams().isEmpty())) {
        myNode.setCodNode(StrCod);

        myNode.setType(RETORNO);
        myNode.setStatusNode(Status.ATIVO);

        List<Node> nds = new ArrayList<>();
        nds.add(myNode);

        upstream.setNodes(nds);

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

      FacesUtils.addInfoMessage("Retorno " + myNode.getCodNode() + " adicionado na up "
          + upstream.getNumUpStream());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);

      if (!nodeList.contains(myNode)) {
        nodeList.add(myNode);
      }

    } else {
      FacesUtils.addInfoMessage("O Retorno " + StrCod + " já existe nessa up! "
          + upstream.getNumUpStream());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    }

    inputCodNode = "";

    System.gc();

  }

  public void onCellEdit(Downstream downstream) {

    String StrCod = codNode + "-" + inputCodNode;

    Node myNode = getNodeIfAlreadyExists(StrCod, DIRETO);

    for (Node direto : nodeList) {
      if (direto.getCodNode().equalsIgnoreCase(StrCod) && direto.getType().equalsIgnoreCase(DIRETO)) {
        myNode = direto;
      }
    }

    if ((downstream.getNodes() == null) || (!downstream.getNodes().contains(myNode))) {

      if ((myNode.getDownstreams() == null) || (myNode.getDownstreams().isEmpty())) {
        myNode.setCodNode(StrCod);

        myNode.setType(DIRETO);
        myNode.setStatusNode(Status.ATIVO);


        List<Node> nds = new ArrayList<>();
        nds.add(myNode);

        downstream.setNodes(nds);

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

      FacesUtils.addInfoMessage("O Direto " + myNode.getCodNode() + " adicionado na down "
          + downstream.getNumDownStream());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);

      if (!nodeList.contains(myNode)) {
        nodeList.add(myNode);
      }

    } else {
      FacesUtils.addInfoMessage("O Retorno " + StrCod + " já existe nessa down! "
          + downstream.getNumDownStream());
      FacesUtils.getExternalContext().getFlash().setKeepMessages(true);
    }

    inputCodNode = "";

    System.gc();

  }

  public void removeNode(Upstream up) {

    List<Node> myListNodes = up.getNodes();

    for (Iterator<Node> iterator = myListNodes.iterator(); iterator.hasNext();) {
      Node myNode = iterator.next();

      if (myNode.getCodNode().contains(codNode)) {
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

      if (myNode.getCodNode().contains(codNode)) {
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

  public List<Upstream> retornoList() {

    List<Upstream> ups = new ArrayList<>();

    if (null != placa.getConectorList()) {
      for (Conector conector : placa.getConectorList()) {
        if (null != conector.getUpstreamList()) {
          for (Upstream upstream : conector.getUpstreamList()) {
            if (!ups.contains(upstream)) {
              ups.add(upstream);
            }
          }
        }
      }
    } else {
      for (Node node : nodes) {
        if (null != node.getUpstreams()) {
          for (Upstream upstream : node.getUpstreams()) {
            if (!ups.contains(upstream)) {
              ups.add(upstream);
            }
          }
        }
      }
    }

    System.gc();

    return ups;
  }

  public List<Downstream> diretoList() {
    List<Downstream> downs = new ArrayList<>();

    if (null != placa.getConectorList()) {
      for (Conector conector : placa.getConectorList()) {
        if (null != conector.getDownstreamList()) {
          for (Downstream down : conector.getDownstreamList()) {
            if (!downs.contains(down)) {
              downs.add(down);
            }
          }
        }
      }
    } else {
      for (Node node : nodes) {
        if (null != node.getDownstreams()) {
          for (Downstream down : node.getDownstreams()) {
            if (!downs.contains(node)) {
              downs.add(down);
            }
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

    System.gc();

  }

  /**
   * @return the inputCodNode
   */
  public String getInputCodNode() {
    return inputCodNode;
  }

  /**
   * @param inputCodNode the inputCodNode to set
   */
  public void setInputCodNode(String inputCodNode) {
    this.inputCodNode = inputCodNode.toUpperCase();
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
   * @return the nodeList
   */
  public List<Node> getNodeList() {
    return nodeList;
  }

  /**
   * @param list the list of nodes to be set
   */
  public void setNodeList(List<Node> list) {
    this.nodeList = list;
  }

}
