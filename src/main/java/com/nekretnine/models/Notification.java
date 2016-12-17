package com.nekretnine.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String text;
	
	@Column(nullable = false)
	private String nType;
	
	@OneToOne
	private User toUser;
	
	@OneToOne
	private User fromUser;
	
	@Column(nullable = true)
	private Date made;
	
	@Column(nullable = false)
	private boolean seen;
	
	@Column(nullable = false)
	private String status;// old or new
	
	public Notification(){}

	public Notification(Long id, String text, String nType, User toUser,
			User fromUser, Date made, boolean seen, String status) {
		super();
		this.id = id;
		this.text = text;
		this.nType = nType;
		this.toUser = toUser;
		this.fromUser = fromUser;
		this.made = made;
		this.seen = seen;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getnType() {
		return nType;
	}

	public void setnType(String nType) {
		this.nType = nType;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public Date getMade() {
		return made;
	}

	public void setMade(Date made) {
		this.made = made;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
