package com.nekretnine.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Rate {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private double advertisementRate;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Customer customer;

	public Rate() {}
	public Rate(double advertisementRate, Customer customer) {
		super();
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
