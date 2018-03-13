package com.uniovi.entities;

import javax.persistence.*;

@Entity
public class Invitation {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	@JoinColumn(name = "invitado_id")
	private User invitado;
	@ManyToOne
	@JoinColumn(name = "invitador_id")
	private User invitador;
	private boolean aceptada;

	public Invitation() {

	}

	public Invitation(User invitado, User invitador, boolean aceptada) {
		this.invitado = invitado;
		this.invitador = invitador;
		this.aceptada = aceptada;
	}

	public User getInvitado() {
		return invitado;
	}

	public void setInvitado(User invitado) {
		this.invitado = invitado;
	}

	public User getInvitador() {
		return invitador;
	}

	public void setInvitador(User invitador) {
		this.invitador = invitador;
	}

	public boolean isAceptada() {
		return aceptada;
	}

	public void setAceptada(boolean aceptada) {
		this.aceptada = aceptada;
	}

	public long getId() {
		return id;
	}

}
