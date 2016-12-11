package com.nekretnine.dto;

import com.nekretnine.models.Rate;

public class RateDTO {

	private Long id;
	private double advertisementRate;
	private CustomerDTO customer;
	
	public RateDTO() {
		super();
	}

	public RateDTO(Long id, double advertisementRate, CustomerDTO customer) {
		super();
		this.id = id;
		this.advertisementRate = advertisementRate;
		this.customer = customer;
	}
	
	public RateDTO(Rate rate) {
		this.id = rate.getId();
		this.advertisementRate = rate.getAdvertisementRate();
		this.customer = new CustomerDTO(rate.getCustomer());
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

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
	
}
