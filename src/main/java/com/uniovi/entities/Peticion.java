package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Peticion {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private User enviada;
	
	@ManyToOne
	private User recibida;
	
	public Peticion() {}
	
	public Peticion (User enviada, User recibida) {
		this.enviada=enviada;
		this.recibida=recibida;
	}
	
	public User getEnviada() {
		return enviada;
	}
	
	public User getRecibida() {
		return recibida;
	}

}
