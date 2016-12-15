package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Advertiser extends User{

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Company company;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="advertiser")
	private Set<Advertisement> advertisements = new HashSet<Advertisement>();

	public Advertiser() {
		super();
	}

	public Advertiser(Company company, Set<Advertisement> advertisements) {
		super();
		this.company = company;
		this.advertisements = advertisements;
	}

	public Advertiser(User owner) {
		super(owner);
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Set<Advertisement> getAdvertisements() {
		return advertisements;
	}

	public void setAdvertisements(Set<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

	
}
