package com.nodemanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private String codHub;

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

}
