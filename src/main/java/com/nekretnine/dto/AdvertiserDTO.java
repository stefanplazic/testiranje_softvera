package com.nekretnine.dto;

import java.util.Set;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.RateAdvertiser;

public class AdvertiserDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private double avRate;
	
	
	public AdvertiserDTO() {}
	
	public AdvertiserDTO(Advertiser advertiser) {
		this.id = advertiser.getId();
		this.firstName = advertiser.getFirstName();
		this.lastName = advertiser.getLastName();
		this.email = advertiser.getEmail();
		this.username = advertiser.getUsername();
		this.avRate = AdvertiserDTO.calculateAverage(advertiser.getRates());
	}
	
	/**
	 * 
	 * @param rates all customer rates for given advertiser
	 * @return Avrage of all rates
	 */
	public static double calculateAverage(Set<RateAdvertiser> rates){
		if(rates.size()==0 || rates == null)
			return 0;
		double res = 0;
		for(RateAdvertiser rate : rates){
			res += rate.getAdvertRate();
		}
		return res/ rates.size();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getAvRate() {
		return avRate;
	}

	public void setAvRate(double avRate) {
		this.avRate = avRate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
