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
public class RateAdvertiser {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, name = "rate")
	private double advertRate;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "customAdv")
	private Customer customAdv;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "advertiserRate")
	private Advertiser advertiserRate;
	
	public RateAdvertiser() {}

	public RateAdvertiser(Long id, double advertRate, Customer customAdv, Advertiser advertiserRate) {
		super();
		this.id = id;
		this.advertRate = advertRate;
		this.customAdv = customAdv;
		this.advertiserRate = advertiserRate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public double getAdvertRate() {
		return advertRate;
	}


	public void setAdvertRate(double advertRate) {
		this.advertRate = advertRate;
	}


	public Customer getCustomAdv() {
		return customAdv;
	}


	public void setCustomAdv(Customer customAdv) {
		this.customAdv = customAdv;
	}


	public Advertiser getAdvertiserRate() {
		return advertiserRate;
	}


	public void setAdvertiserRate(Advertiser advertiserRate) {
		this.advertiserRate = advertiserRate;
	}

	

}
