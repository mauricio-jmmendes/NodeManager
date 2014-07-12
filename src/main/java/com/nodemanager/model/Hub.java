package com.nodemanager.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "HUB")
@SequenceGenerator(name = "seq", sequenceName = "seq_hub", allocationSize = 1, initialValue = 1)
public class Hub {

	@Id
	@GeneratedValue(generator = "seq")
	private Long id;

	@NaturalId
	@Column(unique = true, nullable = false)
	private String codHub;

	@OneToMany(mappedBy = "hub", targetEntity = Cmts.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Cmts> cmtsList;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the codHub
	 */

	public String getCodHub() {
		return codHub;
	}

	/**
	 * @param codHub
	 *            the codHub to set
	 */
	public void setCodHub(String codHub) {
		this.codHub = codHub;
	}

	/**
	 * @return the cmtsList
	 */
	public List<Cmts> getCmtsList() {
		return cmtsList;
	}

	/**
	 * @param cmtsList
	 *            the cmtsList to set
	 */
	public void setCmtsList(List<Cmts> cmtsList) {
		this.cmtsList = cmtsList;
	}

}
