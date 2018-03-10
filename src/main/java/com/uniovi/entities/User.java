package com.uniovi.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
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
	
	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	//private Set<User> friends;

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

	/*public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public Set<User> getFriends() {
		return friends;
	}*/
	
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
	
	

}
