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
	private double advertisementRate;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer")
	private Customer customer;

	public RateEstate() {}

	public RateEstate(Long id, double advertisementRate, Customer customer) {
		super();
		this.id = id;
		this.advertisementRate = advertisementRate;
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAdvertisementRate() {
		return advertisementRate;
	}

	public void setAdvertisementRate(double advertisementRate) {
		this.advertisementRate = advertisementRate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
