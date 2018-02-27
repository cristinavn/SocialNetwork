package com.uniovi.entities;

import javax.persistence.*;

@Entity
public class Mark {

	@Id @GeneratedValue
	private Long id;
	private String description;
	private Double score;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Mark() {}

	public Mark(Long id, String description, Double score) {
		super();
		this.id = id;
		this.description = description;
		this.score = score;
	}

	public Mark(String description, Double score, User user) {
		super();
		this.description = description;
		this.score = score;
		this.user = user;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Mark [id=" + id + ", description=" + description + ", score=" + score + "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user ) {
		this.user = user;
	}


}
