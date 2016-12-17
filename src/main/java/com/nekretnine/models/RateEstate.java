package com.nekretnine.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RateEstate {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, name = "rate")
	private double estateRate;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer")
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "estate")
	private Estate estate;

	public RateEstate() {}

	public RateEstate(Long id, double estateRate, Customer customer, Estate estate) {
		super();
		this.id = id;
		this.estateRate = estateRate;
		this.customer = customer;
		this.estate = estate;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAdvertisementRate() {
		return estateRate;
	}

	public void setAdvertisementRate(double advertisementRate) {
		this.estateRate = advertisementRate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

	
	
}
