package com.uniovi.entities;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique=true)
	private String email;
	private String name;

	private String password;
	@Transient //propiedad que no se almacena en la tabla.
	private String passwordConfirm;

	private String role;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Post> posts;
	
	@OneToMany(mappedBy ="recibida" , cascade =  CascadeType.ALL)
	private Set<Invitation> peticionesRecibidas = new HashSet<>();
	
	@OneToMany(mappedBy ="enviada" , cascade =  CascadeType.ALL)
	private Set<Invitation> peticionesEnviadas = new HashSet<>();

	public User(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPosts(Set<Post> posts) {
		for (Post p : posts) {
			p.setUser(this);
		}
		this.posts = posts;
	}
	
	public Set<Post> getPosts() {
		return this.posts;
	}
	
	public Set<Invitation> getRecibidas(){
		return peticionesRecibidas;
	}

	public Set<Invitation> getEnviadas(){
		return peticionesEnviadas;
	}
	public boolean isRecibida() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		for(Invitation i: peticionesRecibidas) {
			if(i.getEnviada().email.equals(email)) return true;
		}
		return false;
	}
}
