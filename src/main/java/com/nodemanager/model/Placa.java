package com.nodemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nodemanager.util.Status;

@Entity
@Table(name = "PLACA")
@SequenceGenerator(name = "seq", sequenceName = "seq_placa", allocationSize = 1, initialValue = 1)
public class Placa {

	@Id
	@GeneratedValue(generator = "seq")
	private Long id;

	@Column(name = "cod_placa")
	private String codPlaca;
	
	@Column(name = "status_placa")
	Status statusPlaca;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSLOTS")
	Slots slots;
}
