package com.nekretnine.dto;

import com.nekretnine.models.Company;

public class CompanyDTO {

	private Long id;
	private String name;
	private boolean approved;
	private AdvertiserDTO owner;
	
	public CompanyDTO() {}
	
	public CompanyDTO(Long id, String name, boolean approved, AdvertiserDTO owner) {
		super();
		this.id = id;
		this.name = name;
		this.approved = approved;
		this.owner = owner;
	}
	public CompanyDTO(Company company) {
		this.id = company.getId();
		this.name = company.getName();
		this.approved = company.isApproved();
		this.owner = new AdvertiserDTO(company.getOwner());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public AdvertiserDTO getOwner() {
		return owner;
	}

	public void setOwner(AdvertiserDTO owner) {
		this.owner = owner;
	}
	
	
	
	
	
}
