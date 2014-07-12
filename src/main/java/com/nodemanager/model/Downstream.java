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
@Table(name = "DOWNSTREAM")
@SequenceGenerator(name = "seq", sequenceName = "seq_downstream", allocationSize = 1, initialValue = 1)
public class Downstream {

	@Id
	@GeneratedValue(generator = "seq")
	private Long id;

	@Column(name = "status_down", nullable = false)
	private Status statusDown;

	@Column(name = "num_downstream", nullable = false)
	private int numDownStream;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "conector_id")
	private Conector conectorDown;

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
	 * @return the statusDown
	 */
	public Status getStatusDown() {
		return statusDown;
	}

	/**
	 * @param statusDown
	 *            the statusDown to set
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
	 * @param numDownStream
	 *            the numDownStream to set
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
	 * @param conectorDown
	 *            the conectorDown to set
	 */
	public void setConectorDown(Conector conectorDown) {
		this.conectorDown = conectorDown;
	}

}
