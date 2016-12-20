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

import com.nekretnine.dto.AdvertisementDTO;

@Entity
public class Advertisement {

	public enum State {
		OPEN, EXPIRED, REPORTED, REMOVED, SOLD, RENTED
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
	private State state;
	
	@ManyToOne
	@JoinColumn(name = "advertiser")
	private Advertiser advertiser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "estate")
	private Estate estate;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "soldto")
	private Customer soldto;
	
	
	public Advertisement() {
		super();
	}

	public Advertisement(Long id, Date publicationDate, Date expiryDate, 
			State state, Advertiser advertiser, Estate estate) {
		super();
		this.id = id;
		this.publicationDate = publicationDate;
		this.expiryDate = expiryDate;
		this.state = state;
		this.advertiser = advertiser;
		this.estate = estate;

	}
	
	public Advertisement(AdvertisementDTO a){
		this(a.getId(),a.getPublicationDate(),a.getExpiryDate()
				,a.getState(),new Advertiser(a.getAdvertiser()),new Estate(a.getEstate()));
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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

	public Customer getSoldto() {
		return soldto;
	}

	public void setSoldto(Customer soldto) {
		this.soldto = soldto;
	}

}
