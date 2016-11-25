package com.nekretnine.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Advertisement {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private Date publicationDate;
	
	@Column(nullable = false)
	private Date expiryDate;
	
	@Column(nullable = false)
	private Date lastUpdate;
	
	@Column(nullable = false)
	private String contactInfo;
	
	@OneToMany(mappedBy="advertisement", fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<Comment>();

	public Advertisement() { }

	public Advertisement(Long id, Date publicationDate, Date expiryDate, Date lastUpdate, String contactInfo,
			Set<Comment> comments) {
		super();
		this.id = id;
		this.publicationDate = publicationDate;
		this.expiryDate = expiryDate;
		this.lastUpdate = lastUpdate;
		this.contactInfo = contactInfo;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
}
