package com.nekretnine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long userId;
	
	@Column(nullable = true)
	private String first_name;
	
	@Column(nullable = true)
	private String last_name;
	
	@Column(nullable = true, unique = true)
	private String email;
	
	@Column(nullable = true, unique = true)
	private String username;
	
	@Column(nullable = true)
	private String password;
	
	public User() {}

	public User(String first_name, String last_name, String email, String username, String password) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
