package com.nodemanager.util;

public enum StatusProjeto {

  EM_ANDAMENTO(0, "Em andamento"), CONCLUIDO(1, "Concluido"), CANCELADO(2, "Cancelado");

  private String name;
  private int id;

  StatusProjeto(int id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

}
