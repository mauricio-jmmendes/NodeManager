package com.nodemanager.managedbean;

import javax.annotation.PostConstruct;

import com.nodemanager.dao.utils.SimpleEntityManager;
import com.nodemanager.model.Node;
import com.nodemanager.service.NodeService;

public class NodeMB {

  String persistenceUnitName = "NodeManagerPU";
  private SimpleEntityManager simpleEntityManager;

  Node node;
  NodeService nodeService;

  @PostConstruct
  public void init() {
    simpleEntityManager = new SimpleEntityManager(persistenceUnitName);
    nodeService = new NodeService(simpleEntityManager);
    node = new Node();
  }

}
