package com.uniovi.entities;

import javax.persistence.*;

@Entity
public class Friend {

	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	@JoinColumn(name = "userA")
	private User userA;
	@ManyToOne
	@JoinColumn(name = "userB")
	private User userB;
	
	public Friend() {
		
	}
	
	public Friend(User userA, User userB) {
		this.userA = userA;
		this.userB = userB;
	}

	public long getId() {
		return id;
	}

	public User getUserA() {
		return userA;
	}

	public void setUserA(User userA) {
		this.userA = userA;
	}

	public User getUserB() {
		return userB;
	}

	public void setUserB(User userB) {
		this.userB = userB;
	}
	
	
}
