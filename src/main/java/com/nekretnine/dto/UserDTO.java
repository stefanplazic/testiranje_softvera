package com.nekretnine.dto;

import com.nekretnine.models.User;

public class UserDTO {
    
	private Long userId;
	private String first_name;
	private String last_name;
	private String email;
	private String username;
	private String password;
	
	public UserDTO() {}

	public UserDTO(Long userId, String first_name, String last_name, String email, String username, String password) {
		super();
		this.userId = userId;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public UserDTO(User user) {
		this.userId = user.getUserId();
		this.first_name = user.getFirst_name();
		this.last_name = user.getLast_name();
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
