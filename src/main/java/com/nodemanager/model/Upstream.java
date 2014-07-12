package com.nodemanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nodemanager.util.Status;

@Entity
@Table(name = "UPSTREAM")
@SequenceGenerator(name = "seq", sequenceName = "seq_upstream", allocationSize = 1, initialValue = 1)
public class Upstream {

	@Id
	@GeneratedValue(generator = "seq")
	private Long id;

	@Column(name = "status_up", nullable = false)
	private Status statusUp;

	@Column(name = "num_upstream", nullable = false)
	private int numUpStream;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "conector_id")
	private Conector conectorUp;

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
	 * @return the statusUp
	 */
	public Status getStatusUp() {
		return statusUp;
	}

	/**
	 * @param statusUp
	 *            the statusUp to set
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
	 * @param numUpStream
	 *            the numUpStream to set
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
	 * @param conectorUp
	 *            the conectorUp to set
	 */
	public void setConectorUp(Conector conectorUp) {
		this.conectorUp = conectorUp;
	}

}
