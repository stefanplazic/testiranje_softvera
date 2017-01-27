package com.nekretnine.dto;

import com.nekretnine.models.RateAdvertiser;

public class RateDTO {

	private double rate;

	public RateDTO() {
		super();
	}
	
	public RateDTO(RateAdvertiser advertRate) {
		rate = advertRate.getAdvertRate();
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
