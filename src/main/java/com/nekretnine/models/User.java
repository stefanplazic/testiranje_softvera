package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = true)
	private String firstName;
	
	@Column(nullable = true)
	private String lastName;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Report> reports = new HashSet<Report>();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>();
	
	@Column(nullable = true)
	private boolean isVerified;
	
	@Column(nullable = true)
	private String verifyCode;
	
	 @OneToOne(fetch=FetchType.EAGER)
	 @JoinColumn(name="account")
	 private Account account;

	public User() {
		super();
	}
	
	public User(Long id) {
		super();
		this.id = id;
	}

	public User(Long id, String firstName, String lastName, String email, String username, String password,
			Set<Report> reports, Set<UserAuthority> userAuthorities, boolean isVerified, String verifyCode,
			Account account) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.reports = reports;
		this.userAuthorities = userAuthorities;
		this.isVerified = isVerified;
		this.verifyCode = verifyCode;
		this.account = account;
	}

	public User(User user) {
		id = user.getId();
		firstName = user.getFirstName();
		lastName = user.getLastName();
		email = user.getEmail();
		username = user.getUsername();
		password = user.getPassword();
		reports = user.getReports();
		account = user.getAccount();
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

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}
