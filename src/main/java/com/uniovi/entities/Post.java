package com.uniovi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Post {

	@Id @GeneratedValue
	private Long id;
	private String description;
	private String title;
	private String imageUrl;
	
	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Post() {}

	public Post(String title, String description){
		super();
		this.title = title;
		this.description = description;
		this.date = new Date();
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

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + ", title=" + title + ", user=" + user + "]";
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean hasPhoto() {
		if(imageUrl!=null)return true;
		return false;
	}
	
}
