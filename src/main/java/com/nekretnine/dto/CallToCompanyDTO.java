package com.nekretnine.dto;

import java.util.Date;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.CallToCompany;

public class CallToCompanyDTO {

	private Long id;
	private Date DateOfCall;
	private AdvertiserDTO fromadvrt;
	private AdvertiserDTO toadvrt;
	
	public CallToCompanyDTO() {}
	
	
	public CallToCompanyDTO(Long id, Date dateOfCall, AdvertiserDTO fromadvrt, AdvertiserDTO toadvrt) {
		super();
		this.id = id;
		DateOfCall = dateOfCall;
		this.fromadvrt = fromadvrt;
		this.toadvrt = toadvrt;
	}

	public CallToCompanyDTO(CallToCompany callToCompany) {
		this.id = callToCompany.getId();
		this.DateOfCall = callToCompany.getDateOfCall();
		this.fromadvrt = new AdvertiserDTO(callToCompany.getFromAdvertiser());
		this.toadvrt = new AdvertiserDTO(callToCompany.getToAdvertiser());
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDateOfCall() {
		return DateOfCall;
	}
	public void setDateOfCall(Date dateOfCall) {
		DateOfCall = dateOfCall;
	}
	public AdvertiserDTO getFromadvrt() {
		return fromadvrt;
	}
	public void setFromadvrt(AdvertiserDTO fromadvrt) {
		this.fromadvrt = fromadvrt;
	}
	public AdvertiserDTO getToadvrt() {
		return toadvrt;
	}
	public void setToadvrt(AdvertiserDTO toadvrt) {
		this.toadvrt = toadvrt;
	}
	
	
}
