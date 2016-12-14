package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.nekretnine.dto.ReportDTO;
import com.nekretnine.dto.UserDTO;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = true)
	private String firstName;
	
	@Column(nullable = true)
	private String lastName;
	
	@Column(nullable = true, unique = true)
	private String email;
	
	@Column(nullable = true, unique = true)
	private String username;
	
	@Column(nullable = true)
	private String password;
	
	@OneToMany
	private Set<Report> reports = new HashSet<Report>();

	public User() {
		super();
	}

	public User(Long id, String firstName, String lastName, String email, String username, String password,
			Set<Report> reports) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.reports = reports;
	}
	
	public User(UserDTO userdto) {
		this.id = userdto.getId();
		this.firstName = userdto.getFirstName();
		this.lastName = userdto.getLastName();
		this.email = userdto.getEmail();
		this.username = userdto.getUsername();
		this.password = userdto.getPassword();
		setReports(userdto.getReports());	
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

	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<?> reports) {
		this.reports = new HashSet<Report>();
		for(Object obj : reports) {
			if(obj instanceof Report) {
				this.reports.add((Report)obj);
			}
			else if (obj instanceof ReportDTO) {
				this.reports.add(new Report((ReportDTO)obj));
			}
		}
	}
	
}
