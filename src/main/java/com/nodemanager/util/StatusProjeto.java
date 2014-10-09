package com.nodemanager.util;

public enum StatusProjeto {

  INICIADO(0, "Iniciado"), EM_ANDAMENTO(1, "Em andamento"), CONCLUIDO(2, "Concluido");

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
