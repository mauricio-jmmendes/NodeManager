package com.nodemanager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nodemanager.util.Status;

@Entity
@Table(name = "CONECTOR")
@SequenceGenerator(name = "seqCon", sequenceName = "seq_conector", allocationSize = 1,
    initialValue = 1)
public class Conector {

  @Id
  @GeneratedValue(generator = "seqCon")
  private Long id;

  @Column(name = "num_conector", nullable = false)
  private String numConector;

  @Column(name = "status_conector", unique = true, nullable = false)
  private Status statusConector;

  @ManyToOne
  @JoinColumn(name = "placa_id")
  private Placa placa;

  @OneToMany(mappedBy = "conectorUp")
  private List<Upstream> upstreamList;

  @OneToMany(mappedBy = "conectorDown")
  private List<Downstream> downstreamList;

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
   * @return the numConector
   */
  public String getNumConector() {
    return numConector;
  }

  /**
   * @param numConector the numConector to set
   */
  public void setNumConector(String numConector) {
    this.numConector = numConector;
  }

  /**
   * @return the statusConector
   */
  public Status getStatusConector() {
    return statusConector;
  }

  /**
   * @param statusConector the statusConector to set
   */
  public void setStatusConector(Status statusConector) {
    this.statusConector = statusConector;
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

  /**
   * @return the upstreamList
   */
  public List<Upstream> getUpstreamList() {
    return upstreamList;
  }

  /**
   * @param upstreamList the upstreamList to set
   */
  public void setUpstreamList(List<Upstream> upstreamList) {
    this.upstreamList = upstreamList;
  }

  /**
   * @return the downstreamList
   */
  public List<Downstream> getDownstreamList() {
    return downstreamList;
  }

  /**
   * @param downstreamList the downstreamList to set
   */
  public void setDownstreamList(List<Downstream> downstreamList) {
    this.downstreamList = downstreamList;
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
