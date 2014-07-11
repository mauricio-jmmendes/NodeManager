package com.nodemanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.nodemanager.util.Status;


@Entity
@Table(name = "CONECTOR")
@SequenceGenerator(name = "seq", sequenceName = "seq_conector", allocationSize = 1, initialValue = 1)
public class Conector {
	
	@Id
	@GeneratedValue(generator = "seq")
	private Long id;
	
	@Column(name = "cod_conector")
	private String codConector;
	
	@Column(name = "status_conector")
	private String statusConector;
	
	@ManyToOne
	@JoinColumn(name = "idPlaca")
	private Placa placa;
	
	@OneToMany
	@JoinColumn(name = "idUpstream")
	private Upstream upstream;
	
	@OneToMany
	@JoinColumn(name = "idDownstream")
	private Downstream downstream;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodConector() {
		return codConector;
	}

	public void setCodConector(String codConector) {
		this.codConector = codConector;
	}

	public String getStatusConector() {
		return statusConector;
	}

	public void setStatusConector(String statusConector) {
		this.statusConector = statusConector;
	}

	public Placa getPlaca() {
		return placa;
	}

	public void setPlaca(Placa placa) {
		this.placa = placa;
	}

	public Upstream getUpstream() {
		return upstream;
	}

	public void setUpstream(Upstream upstream) {
		this.upstream = upstream;
	}

	public Downstream getDownstream() {
		return downstream;
	}

	public void setDownstream(Downstream downstream) {
		this.downstream = downstream;
	}
	

}
