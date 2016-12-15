package com.nekretnine.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Advertisement {

	public enum State {
		OPEN, EXPIRED, REPORTED, REMOVED, SOLD
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(nullable = false)
	private Date publicationDate;
	
	@Column(nullable = false)
	private Date expiryDate;
	
	@Column(nullable = false)
	private Date lastUpdate;
	
	@Column(nullable = false)
	private String contactInfo;
	
	@Column(nullable = false)
	private State state;
	
	@OneToMany(mappedBy="advertisement", fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<Comment>();
	
	@ManyToOne
	@JoinColumn(name = "advertiser")
	private Advertiser advertiser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estate")
	private Estate estate;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Report> reports = new HashSet<Report>();

	public Advertisement() {
		super();
	}

	public Advertisement(Long id, Date publicationDate, Date expiryDate, Date lastUpdate, String contactInfo,
			State state, Set<Comment> comments, Advertiser advertiser, Estate estate, Set<Report> reports) {
		super();
		this.id = id;
		this.publicationDate = publicationDate;
		this.expiryDate = expiryDate;
		this.lastUpdate = lastUpdate;
		this.contactInfo = contactInfo;
		this.state = state;
		this.comments = comments;
		this.advertiser = advertiser;
		this.estate = estate;
		this.reports = reports;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Set<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

	public Set<Report> getReports() {
		return reports;
	}
	
	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}
	
}
