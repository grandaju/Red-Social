package com.uniovi.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

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
	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;
	@OneToMany(mappedBy = "invitado", cascade = CascadeType.ALL)
	private Set<Invitation> invitaciones = new HashSet<Invitation>();
	
	@OneToOne(mappedBy="invitador",  cascade = CascadeType.ALL)
	private Invitation invitacion = new Invitation();

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

}
