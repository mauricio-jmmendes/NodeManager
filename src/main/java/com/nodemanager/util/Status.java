package com.nodemanager.util;

public enum Status {

  LIVRE(0, "Livre"), OCUPADO(1, "Ocupado"), FUNCIONANDO(2, "Funcionando"), DEFEITO(3, "Com Defeito"), EM_PROJETO(
      3, "Em Projeto"), ATIVO(4, "Ativo");

  private String name;
  private int id;

  Status(int id, String name) {
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
