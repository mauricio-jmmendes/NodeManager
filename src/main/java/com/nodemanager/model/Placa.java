package com.nodemanager.model;

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

import com.nodemanager.util.Status;

@Entity
@Table(name = "PLACA", uniqueConstraints = { @UniqueConstraint(columnNames = "slots_id") })
@SequenceGenerator(name = "seqP", sequenceName = "seq_placa", allocationSize = 1, initialValue = 1)
public class Placa {

	@Id
	@GeneratedValue(generator = "seqP")
	private Long id;

	@Column(name = "cod_placa")
	private String codPlaca;

	@Column(name = "status_placa")
	Status statusPlaca;

	@OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "slots_id", unique = true)
	Slot slot;

	@OneToMany(mappedBy = "placa")
	List<Conector> conectorList;

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
	 * @return the codPlaca
	 */
	public String getCodPlaca() {
		return codPlaca;
	}

	/**
	 * @param codPlaca
	 *            the codPlaca to set
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
	 * @param statusPlaca
	 *            the statusPlaca to set
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
	 * @param slots
	 *            the slots to set
	 */
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
}
