package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.nekretnine.dto.AdvertiserDTO;

@Entity
public class Advertiser extends User{

	@ManyToOne
	@JoinColumn(name="company")
	private Company company;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="advertiser")
	private Set<Advertisement> advertisements = new HashSet<Advertisement>();
	
	
	@OneToMany(mappedBy="advertiserRate", fetch = FetchType.LAZY)
	private Set<RateAdvertiser> rates  = new HashSet<RateAdvertiser>();
	
	@OneToMany(mappedBy="fromadvrt", fetch = FetchType.LAZY)
	private Set<CallToCompany> sendCompanyCalls  = new HashSet<CallToCompany>();
	
	@OneToMany(mappedBy="toadvrt", fetch = FetchType.LAZY)
	private Set<CallToCompany> receivedCompanyCalls  = new HashSet<CallToCompany>();
	
	public Advertiser() {
		super();
	}


	public Advertiser(Company company, Set<Advertisement> advertisements, Set<RateAdvertiser> rates,
			Set<CallToCompany> sendCompanyCalls, Set<CallToCompany> receivedCompanyCalls) {
		super();
		this.company = company;
		this.advertisements = advertisements;
		this.rates = rates;
		this.sendCompanyCalls = sendCompanyCalls;
		this.receivedCompanyCalls = receivedCompanyCalls;
	}

	public Advertiser(AdvertiserDTO a){
		super();
		
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


	public Set<RateAdvertiser> getRates() {
		return rates;
	}


	public void setRates(Set<RateAdvertiser> rates) {
		this.rates = rates;
	}


	public Set<CallToCompany> getSendCompanyCalls() {
		return sendCompanyCalls;
	}


	public void setSendCompanyCalls(Set<CallToCompany> sendCompanyCalls) {
		this.sendCompanyCalls = sendCompanyCalls;
	}


	public Set<CallToCompany> getReceivedCompanyCalls() {
		return receivedCompanyCalls;
	}


	public void setReceivedCompanyCalls(Set<CallToCompany> receivedCompanyCalls) {
		this.receivedCompanyCalls = receivedCompanyCalls;
	}


	

	
}
