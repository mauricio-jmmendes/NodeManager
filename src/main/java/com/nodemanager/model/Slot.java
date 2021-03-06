package com.nodemanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nodemanager.util.Status;

@Entity
@Table(name = "SLOTS")
@SequenceGenerator(name = "seqS", sequenceName = "seq_slots", allocationSize = 1, initialValue = 1)
public class Slot implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "seqS")
  private Long id;

  @Column(name = "cod_slot")
  private String codSlot;

  @Column(name = "status_slot")
  private Status statusSlot;

  @ManyToOne
  @JoinColumn(name = "cmts_id")
  private Cmts cmts;

  @OneToOne(mappedBy = "slot")
  private Placa placa;

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
   * @return the codSlot
   */
  public String getCodSlot() {
    return codSlot;
  }

  /**
   * @param codSlot the codSlot to set
   */
  public void setCodSlot(String codSlot) {
    this.codSlot = codSlot;
  }

  /**
   * @return the statusSlot
   */
  public Status getStatusSlot() {
    return statusSlot;
  }

  /**
   * @param statusSlot the statusSlot to set
   */
  public void setStatusSlot(Status statusSlot) {
    this.statusSlot = statusSlot;
  }

  /**
   * @return the cmts
   */
  public Cmts getCmts() {
    return cmts;
  }

  /**
   * @param cmts the cmts to set
   */
  public void setCmts(Cmts cmts) {
    this.cmts = cmts;
  }

  /**
   * @return the placa
   */
  public Placa getPlaca() {
    return placa;
  }

  /**
   * @param placa the placa to set
   */
  public void setPlaca(Placa placa) {
    this.placa = placa;
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
