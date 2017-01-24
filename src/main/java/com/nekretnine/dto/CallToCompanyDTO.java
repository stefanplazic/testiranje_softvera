package com.nekretnine.dto;

import java.util.Date;

import com.nekretnine.models.CallToCompany;

public class CallToCompanyDTO {

	private Long id;
	private Date dateOfCall;
	private AdvertiserDTO fromadvrt;
	private AdvertiserDTO toadvrt;
	private CompanyDTO company;

	public CallToCompanyDTO() {
		super();
	}

	public CallToCompanyDTO(Long id, Date dateOfCall, AdvertiserDTO fromadvrt, AdvertiserDTO toadvrt) {
		super();
		this.id = id;
		this.dateOfCall = dateOfCall;
		this.fromadvrt = fromadvrt;
		this.toadvrt = toadvrt;
	}

	public CallToCompanyDTO(CallToCompany callToCompany) {
		this.id = callToCompany.getId();
		this.dateOfCall = callToCompany.getDateOfCall();
		this.fromadvrt = new AdvertiserDTO(callToCompany.getFromAdvertiser());
		this.toadvrt = new AdvertiserDTO(callToCompany.getToAdvertiser());
		this.company = new CompanyDTO(callToCompany.getFromAdvertiser().getCompany());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfCall() {
		return dateOfCall;
	}

	public void setDateOfCall(Date dateOfCall) {
		this.dateOfCall = dateOfCall;
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

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

}
