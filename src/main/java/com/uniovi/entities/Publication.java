package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Publication {
	@Id
	@GeneratedValue
	private long id;

	private String title;
	private String text;
	@ManyToOne
	private User user;

	private Date date;

	public Publication() {

	}

	public Publication(long id, String title, String text) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
	}

	public Publication(long id, String title, String text, User user, Date date) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.user = user;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}
}
