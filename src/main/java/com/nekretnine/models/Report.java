package com.nekretnine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Report {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "advertisement")
	private Advertisement advertisement;
	
	@Column
	private String message;
	
	@Column
	private String status;
	
	@Column
	private boolean onHold;

	public Report() {
		super();
	}

	public Report(User user, Advertisement advertisement, String message, String status, boolean onHold) {
		super();
		this.user = user;
		this.advertisement = advertisement;
		this.message = message;
		this.status = status;
		this.onHold = onHold;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isOnHold() {
		return onHold;
	}

	public void setOnHold(boolean onHold) {
		this.onHold = onHold;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", user=" + user + ", advertisement=" + advertisement + ", message=" + message
				+ ", status=" + status + ", onHold=" + onHold + "]";
	}
	
	
}
