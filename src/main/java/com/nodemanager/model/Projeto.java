package com.nodemanager.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nodemanager.util.StatusProjeto;

@Entity
@Table(name = "PROJETO")
@SequenceGenerator(name = "seqProj", sequenceName = "seq_proj", allocationSize = 1,
    initialValue = 1)
public class Projeto implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "seqProj")
  private Long id;

  @Column(name = "usuario", nullable = false)
  private Login usuario;

  @Column(name = "descricao", nullable = false, length = 100)
  private String descricao;

  @Column(name = "obs", nullable = false, length = 500)
  private String obs;

  @Column(name = "node_de", nullable = false, length = 10)
  private String nodeDe;

  @Column(name = "node_para", nullable = false, length = 10)
  private String nodePara;

  @Column(name = "status_projeto", nullable = false)
  private StatusProjeto statusProjeto;

  @Column(name = "dt_projeto", nullable = false)
  @Temporal(value=TemporalType.TIMESTAMP)
  private Date dataProjeto;

  @Column(name = "dt_manobra", nullable = false)
  private Date dataManobra;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the usuario
   */
  public Login getUsuario() {
    return usuario;
  }

  /**
   * @param usuario the usuario to set
   */
  public void setUsuario(Login usuario) {
    this.usuario = usuario;
  }

  /**
   * @return the descricao
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * @param descricao the descricao to set
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  /**
   * @return the obs
   */
  public String getObs() {
    return obs;
  }

  /**
   * @param obs the obs to set
   */
  public void setObs(String obs) {
    this.obs = obs;
  }

  /**
   * @return the nodeDe
   */
  public String getNodeDe() {
    return nodeDe;
  }

  /**
   * @param nodeDe the nodeDe to set
   */
  public void setNodeDe(String nodeDe) {
    this.nodeDe = nodeDe;
  }

  /**
   * @return the nodePara
   */
  public String getNodePara() {
    return nodePara;
  }

  /**
   * @param nodePara the nodePara to set
   */
  public void setNodePara(String nodePara) {
    this.nodePara = nodePara;
  }

  /**
   * @return the dataProjeto
   */
  public Date getDataProjeto() {
    return dataProjeto;
  }

  /**
   * @param dataProjeto the dataProjeto to set
   */
  public void setDataProjeto(Date dataProjeto) {
    this.dataProjeto = dataProjeto;
  }

  /**
   * @return the dataManobra
   */
  public Date getDataManobra() {
    return dataManobra;
  }

  /**
   * @param dataManobra the dataManobra to set
   */
  public void setDataManobra(Date dataManobra) {
    this.dataManobra = dataManobra;
  }

  /**
   * @return the statusProjeto
   */
  public StatusProjeto getStatusProjeto() {
    return statusProjeto;
  }

  /**
   * @param statusProjeto the statusProjeto to set
   */
  public void setStatusProjeto(StatusProjeto statusProjeto) {
    this.statusProjeto = statusProjeto;
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

}
