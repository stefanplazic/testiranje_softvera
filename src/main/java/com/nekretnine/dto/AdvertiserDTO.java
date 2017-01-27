package com.nekretnine.dto;

import java.util.HashSet;
import java.util.Set;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.RateAdvertiser;

public class AdvertiserDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private CompanyDTO company;
	private double avRate;
	private Set<RateDTO> rates = new HashSet<>();

	public AdvertiserDTO() {
		super();
	}

	public AdvertiserDTO(Advertiser advertiser) {
		this.id = advertiser.getId();
		this.firstName = advertiser.getFirstName();
		this.lastName = advertiser.getLastName();
		this.email = advertiser.getEmail();
		this.username = advertiser.getUsername();
		this.avRate = AdvertiserDTO.calculateAverage(advertiser.getRates());
		populateRates(advertiser.getRates());

	}

	public void populateRates(Set<RateAdvertiser> ratess) {
		for (RateAdvertiser rate : ratess)
			rates.add(new RateDTO(rate));

	}

	/**
	 * 
	 * @param rates
	 *            all customer rates for given advertiser
	 * @return Avrage of all rates
	 */
	public static double calculateAverage(Set<RateAdvertiser> rates) {
		if (rates.isEmpty())
			return 0;
		double res = 0;
		for (RateAdvertiser rate : rates) {
			res += rate.getAdvertRate();
		}
		return res / rates.size();
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

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public Set<RateDTO> getRates() {
		return rates;
	}

	public void setRates(Set<RateDTO> rates) {
		this.rates = rates;
	}

}
