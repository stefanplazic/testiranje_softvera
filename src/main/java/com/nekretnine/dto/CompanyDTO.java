package com.nekretnine.dto;

import com.nekretnine.models.Company;

public class CompanyDTO {

	private long id;
	private String name;
	private String address;
	private AdvertiserDTO owner;
	
	public CompanyDTO() {
		super();
	}

	public CompanyDTO(long id, String name, String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public CompanyDTO(Company comp){
		this(comp.getId(), comp.getName(), comp.getAddress());
		this.owner = new AdvertiserDTO(comp.getOwner());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public AdvertiserDTO getOwner() {
		return owner;
	}

	public void setOwner(AdvertiserDTO owner) {
		this.owner = owner;
	}
	
	
	
}
