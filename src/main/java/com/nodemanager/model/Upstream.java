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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nodemanager.util.Status;

@Entity
@Table(name = "UPSTREAM")
@SequenceGenerator(name = "seqU", sequenceName = "seq_upstream", allocationSize = 1,
    initialValue = 1)
public class Upstream implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "seqU")
  private Long id;

  @Column(name = "status_up", nullable = false)
  private Status statusUp;

  @Column(name = "num_upstream", nullable = false)
  private int numUpStream;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "conector_id")
  private Conector conectorUp;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "upstreams")
  private List<Node> nodes;

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
   * @return the statusUp
   */
  public Status getStatusUp() {
    return statusUp;
  }

  /**
   * @param statusUp the statusUp to set
   */
  public void setStatusUp(Status statusUp) {
    this.statusUp = statusUp;
  }

  /**
   * @return the numUpStream
   */
  public int getNumUpStream() {
    return numUpStream;
  }

  /**
   * @param numUpStream the numUpStream to set
   */
  public void setNumUpStream(int numUpStream) {
    this.numUpStream = numUpStream;
  }

  /**
   * @return the conectorUp
   */
  public Conector getConectorUp() {
    return conectorUp;
  }

  /**
   * @param conectorUp the conectorUp to set
   */
  public void setConectorUp(Conector conectorUp) {
    this.conectorUp = conectorUp;
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
