package com.nekretnine.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nekretnine.dto.RateDTO;

@Entity
public class Rate {

	@Id
	@GeneratedValue
	
	private Long id;
	
	@Column(nullable = false, name = "rate")
	private double advertisementRate;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer")
	private Customer customer;

	public Rate() {}

	public Rate(Long id, double advertisementRate, Customer customer) {
		super();
		this.id = id;
		this.advertisementRate = advertisementRate;
		this.customer = customer;
	}
	
	public Rate(RateDTO rtdto) {
		this.id = rtdto.getId();
		this.advertisementRate = rtdto.getAdvertisementRate();
		this.customer = new Customer(rtdto.getCustomer());
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
