package com.nekretnine.dto;

import com.nekretnine.models.User;

public class UserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String authority;

	public UserDTO(Long id, String firstName, String lastName, String email, String username, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public UserDTO() {
	}

	public UserDTO(User u) {
		id = u.getId();
		firstName = u.getFirstName();
		lastName = u.getLastName();
		email = u.getEmail();
		username = u.getUsername();
		// ovo je novo dodatooo - ako smeta , zakomentarisati
		this.authority = u.getUserAuthorities().iterator().next().getAuthority().getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
