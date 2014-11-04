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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.nodemanager.util.Status;

@Entity
@Table(name = "NODE")
@SequenceGenerator(name = "seqN", sequenceName = "seq_node", allocationSize = 1, initialValue = 1)
public class Node implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "seqN")
  private Long id;

  @Column(name = "cod_node", nullable = false, length = 10)
  private String codNode;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "NODE_UPSTREAM", joinColumns = {@JoinColumn(name = "node_id", nullable = false,
      updatable = false)}, inverseJoinColumns = {@JoinColumn(name = "upstream_id",
      nullable = false, updatable = false)})
  private List<Upstream> upstreams;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "NODE_DOWNSTREAM", joinColumns = {@JoinColumn(name = "node_id",
      nullable = false, updatable = false)}, inverseJoinColumns = {@JoinColumn(
      name = "downstream_id", nullable = false, updatable = false)})
  private List<Downstream> downstreams;

  @Column(name = "type_node", nullable = false, length = 10)
  private String type;

  @Column(name = "status_node", nullable = false, length = 10)
  private Status statusNode;

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
   * @return the codNode
   */
  public String getCodNode() {
    return codNode;
  }

  /**
   * @param codNode the codNode to set
   */
  public void setCodNode(String codNode) {
    this.codNode = codNode;
  }

  /**
   * @return the upstreams
   */
  public List<Upstream> getUpstreams() {
    return upstreams;
  }

  /**
   * @param upstreams the upstreams to set
   */
  public void setUpstreams(List<Upstream> upstreams) {
    this.upstreams = upstreams;
  }

  /**
   * @return the downstreams
   */
  public List<Downstream> getDownstreams() {
    return downstreams;
  }

  /**
   * @param downstreams the downstreams to set
   */
  public void setDownstreams(List<Downstream> downstreams) {
    this.downstreams = downstreams;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return the statusNode
   */
  public Status getStatusNode() {
    return statusNode;
  }

  /**
   * @param statusNode the statusNode to set
   */
  public void setStatusNode(Status statusNode) {
    this.statusNode = statusNode;
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
