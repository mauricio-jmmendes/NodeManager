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
@Table(name = "DOWNSTREAM")
@SequenceGenerator(name = "seqD", sequenceName = "seq_downstream", allocationSize = 1,
    initialValue = 1)
public class Downstream implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "seqD")
  private Long id;

  @Column(name = "status_down", nullable = false)
  private Status statusDown;

  @Column(name = "num_downstream", nullable = false)
  private int numDownStream;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "conector_id")
  private Conector conectorDown;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "downstreams")
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
   * @return the statusDown
   */
  public Status getStatusDown() {
    return statusDown;
  }

  /**
   * @param statusDown the statusDown to set
   */
  public void setStatusDown(Status statusDown) {
    this.statusDown = statusDown;
  }

  /**
   * @return the numDownStream
   */
  public int getNumDownStream() {
    return numDownStream;
  }

  /**
   * @param numDownStream the numDownStream to set
   */
  public void setNumDownStream(int numDownStream) {
    this.numDownStream = numDownStream;
  }

  /**
   * @return the conectorDown
   */
  public Conector getConectorDown() {
    return conectorDown;
  }

  /**
   * @param conectorDown the conectorDown to set
   */
  public void setConectorDown(Conector conectorDown) {
    this.conectorDown = conectorDown;
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
