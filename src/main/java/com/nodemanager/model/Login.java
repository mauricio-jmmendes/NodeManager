package com.nodemanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "LOGIN", uniqueConstraints = {
		@UniqueConstraint(columnNames = "login"),
		@UniqueConstraint(columnNames = "senha") })
@SequenceGenerator(name = "seqL", sequenceName = "seq_login", allocationSize = 1, initialValue = 1)
public class Login {

	@Id
	@GeneratedValue(generator = "seqL")
	private Long id;

	@NaturalId
	@Column(length = 45, unique = true, nullable = false)
	private String login;

	@Column(length = 45, unique = true, nullable = false)
	private String senha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
