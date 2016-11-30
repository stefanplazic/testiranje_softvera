package com.nekretnine.dto;

import com.nekretnine.models.User;

public class UserDTO {
    
	private Long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	
	public UserDTO() {}

	public UserDTO(Long userId, String first_name, String last_name, String email, String username, String password) {
		super();
		this.userId = userId;
		this.firstName = first_name;
		this.lastName = last_name;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public UserDTO(User user) {
		this.userId = user.getUserId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String first_name) {
		this.firstName = first_name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String last_name) {
		this.lastName = last_name;
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
