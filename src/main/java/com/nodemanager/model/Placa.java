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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nodemanager.util.Status;

@Entity
@Table(name = "PLACA", uniqueConstraints = {@UniqueConstraint(columnNames = "slots_id")})
@SequenceGenerator(name = "seqP", sequenceName = "seq_placa", allocationSize = 1, initialValue = 1)
public class Placa implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "seqP")
  private Long id;

  @Column(name = "cod_placa")
  private String codPlaca;

  @Column(name = "status_placa")
  Status statusPlaca;

  @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER,
      orphanRemoval = true)
  @JoinColumn(name = "slots_id", unique = true)
  Slot slot;

  @OneToMany(mappedBy = "placa", targetEntity = Conector.class, cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  List<Conector> conectorList;

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
   * @return the codPlaca
   */
  public String getCodPlaca() {
    return codPlaca;
  }

  /**
   * @param codPlaca the codPlaca to set
   */
  public void setCodPlaca(String codPlaca) {
    this.codPlaca = codPlaca;
  }

  /**
   * @return the statusPlaca
   */
  public Status getStatusPlaca() {
    return statusPlaca;
  }

  /**
   * @param statusPlaca the statusPlaca to set
   */
  public void setStatusPlaca(Status statusPlaca) {
    this.statusPlaca = statusPlaca;
  }

  /**
   * @return the slots
   */
  public Slot getSlot() {
    return slot;
  }

  /**
   * @param slots the slots to set
   */
  public void setSlot(Slot slot) {
    this.slot = slot;
  }

  /**
   * @return the conectorList
   */
  public List<Conector> getConectorList() {
    return conectorList;
  }

  /**
   * @param conectorList the conectorList to set
   */
  public void setConectorList(List<Conector> conectorList) {
    this.conectorList = conectorList;
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
