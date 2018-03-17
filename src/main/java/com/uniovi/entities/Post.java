package com.uniovi.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post {

	@Id @GeneratedValue
	private Long id;
	private String description;
	private String title;
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Post() {}
	
	public Post(String title, String description){
		super();
		this.title = title;
		this.description = description;
		this.date = LocalDate.now();
	}
	
	public Post(Long id, String title, String description) {
		this(title, description);
		this.id= id;
	}
	
	public Post(String title, String description, User user) {
		this(title, description);
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + ", title=" + title + ", user=" + user + "]";
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
