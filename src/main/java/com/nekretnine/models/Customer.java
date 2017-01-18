package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Customer extends User {

	@Column(nullable = false)
	@OneToMany(mappedBy="customer", fetch = FetchType.LAZY)
	private Set<RateEstate> rates  = new HashSet<>();
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private Set<Comment> comments = new HashSet<>();
	
	@OneToMany(mappedBy="customAdv", fetch = FetchType.LAZY)
	private Set<RateAdvertiser> advRates  = new HashSet<>();
	
	@OneToMany(mappedBy="soldto", fetch = FetchType.LAZY)
	private Set<Advertisement> boughtAdvertisement  = new HashSet<>();

	@OneToMany(mappedBy="customer", fetch = FetchType.LAZY)
	private Set<Favourites> favourites  = new HashSet<>();
	
	public Customer() {
		super();
	}

	public Customer(Set<RateEstate> rates, Set<Comment> comments, Set<RateAdvertiser> advRates,
			Set<Advertisement> boughtAdvertisement) {
		super();
		this.rates = rates;
		this.comments = comments;
		this.advRates = advRates;
		this.boughtAdvertisement = boughtAdvertisement;
	}
	
	public Set<RateEstate> getRates() {
		return rates;
	}

	public void setRates(Set<RateEstate> rates) {
		this.rates = rates;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}


	public Set<RateAdvertiser> getAdvRates() {
		return advRates;
	}

	public void setAdvRates(Set<RateAdvertiser> advRates) {
		this.advRates = advRates;
	}

	public Set<Advertisement> getBoughtAdvertisement() {
		return boughtAdvertisement;
	}

	public void setBoughtAdvertisement(Set<Advertisement> boughtAdvertisement) {
		this.boughtAdvertisement = boughtAdvertisement;
	}
	
}
