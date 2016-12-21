package com.nekretnine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Report {

	public enum State {
		OPEN, CLOSED
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "advertisement")
	private Advertisement advertisement;
	
	@Column
	private String message;
	
	@Column
	@Enumerated(EnumType.STRING)
	private State state;

	public Report() {
		super();
	}

	public Report(Long id, User user, Advertisement advertisement, String message, State state) {
		super();
		this.id = id;
		this.user = user;
		this.advertisement = advertisement;
		this.message = message;
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
