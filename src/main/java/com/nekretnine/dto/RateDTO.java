package com.nekretnine.dto;

import com.nekretnine.models.RateAdvertiser;

public class RateDTO {

	private double rate;
	private UserDTO user;

	public RateDTO() {
		super();
	}

	public RateDTO(RateAdvertiser advertRate) {
		rate = advertRate.getAdvertRate();
		user = new UserDTO(advertRate.getCustomAdv());
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
