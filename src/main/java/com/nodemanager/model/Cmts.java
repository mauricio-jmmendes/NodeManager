package com.nodemanager.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "CMTS", uniqueConstraints = {@UniqueConstraint(columnNames = "nome"),
    @UniqueConstraint(columnNames = "ip")})
@SequenceGenerator(name = "seqC", sequenceName = "seq_cmts", allocationSize = 1, initialValue = 1)
public class Cmts implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "seqC")
  private Long id;

  @NaturalId
  @Column(length = 45, unique = true, nullable = false)
  private String nome;

  @Column(length = 45, unique = true, nullable = false)
  private String ip;

  @Column(length = 45, nullable = false)
  private String marca;

  @Column(length = 45, nullable = false)
  private String modelo;

  @OneToMany(mappedBy = "cmts", targetEntity = Slot.class, cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  private List<Slot> slots;

  @ManyToOne
  @JoinColumn(name = "hub_id", nullable = false)
  private Hub hub;

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
   * @return the nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * @param nome the nome to set
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * @return the ip
   */
  public String getIp() {
    return ip;
  }

  /**
   * @param ip the ip to set
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

  /**
   * @return the marca
   */
  public String getMarca() {
    return marca;
  }

  /**
   * @param marca the marca to set
   */
  public void setMarca(String marca) {
    this.marca = marca;
  }

  /**
   * @return the modelo
   */
  public String getModelo() {
    return modelo;
  }

  /**
   * @param modelo the modelo to set
   */
  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  /**
   * @return the slots
   */
  public List<Slot> getSlots() {
    return slots;
  }

  /**
   * @param slots the slots to set
   */
  public void setSlots(List<Slot> slots) {
    this.slots = slots;
  }

  /**
   * @return the hub
   */
  public Hub getHub() {
    return hub;
  }

  /**
   * @param hub the hub to set
   */
  public void setHub(Hub hub) {
    this.hub = hub;
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
