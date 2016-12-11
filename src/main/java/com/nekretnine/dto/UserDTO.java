package com.nekretnine.dto;

import java.util.HashSet;
import java.util.Set;

import com.nekretnine.models.Report;
import com.nekretnine.models.User;

public class UserDTO {
    
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private Set<ReportDTO> reports = new HashSet<ReportDTO>();
	
	public UserDTO() {}
	
	public UserDTO(Long id, String firstName, String lastName, String email, String username, String password,
			Set<ReportDTO> reports) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.reports = reports;
	}

	public UserDTO(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.password = user.getPassword();
		for(Report rep : user.getReports()) {
			this.reports.add(new ReportDTO(rep));
		}
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

	public Set<ReportDTO> getReports() {
		return reports;
	}

	public void setReports(Set<ReportDTO> reports) {
		this.reports = reports;
	}

	@Override
	public String toString() {
		return "UserDTO [userId=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", username=" + username + ", password=" + password + "]";
	}
	
}
