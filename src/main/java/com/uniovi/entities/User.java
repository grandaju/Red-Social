package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String lastName;
	private String role;


	private String password;
	@Transient 
	private String passwordConfirm;
	@OneToMany(mappedBy = "invitado", cascade = CascadeType.ALL)
	private Set<Invitation> invitaciones;
	
	@OneToMany(mappedBy="invitador",  cascade = CascadeType.ALL)
	private Set<Invitation> invitacionesEnviadas;
	
	
	@OneToMany(mappedBy="userA", cascade = CascadeType.ALL)
	private Set<Friend> amigos;

	@OneToMany(mappedBy="userB", cascade = CascadeType.ALL)
	private Set<Friend> amigosB;

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private Set<Publication> publications;
	
	
	
	public User(String email, String name, String lastName) {
		super();
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public User() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFullName() {
		return this.name + " " + this.lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Publication> getPublications() {
		return publications;
	}

	public void setPublications(Set<Publication> publications) {
		this.publications = publications;
	}

	public Set<Invitation> getInvitaciones() {
		return invitaciones;
	}

	public void setInvitaciones(Set<Invitation> invitaciones) {
		this.invitaciones = invitaciones;
	}

	public Set<Invitation> getInvitacionesEnviadas() {
		return invitacionesEnviadas;
	}

	public void setInvitacionesEnviadas(Set<Invitation> invitacionesEnviadas) {
		this.invitacionesEnviadas = invitacionesEnviadas;
	}

	public Set<Friend> getAmigos() {
		return amigos;
	}

	public void setAmigos(Set<Friend> amigos) {
		this.amigos = amigos;
	}

	public Set<Friend> getAmigosB() {
		return amigosB;
	}

	public void setAmigosB(Set<Friend> amigosB) {
		this.amigosB = amigosB;
	}

}
