package com.nekretnine.dto;

import com.nekretnine.models.Company;

public class CompanyDTO {

	private long id;
	private String name;
	private String address;
	private AdvertiserDTO owner;
	private boolean onHold;

	public CompanyDTO() {
		super();
	}

	public CompanyDTO(long id, String name, String address, boolean onHold) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.onHold = onHold;
	}

	public CompanyDTO(Company comp){
		this(comp.getId(), comp.getName(), comp.getAddress(), comp.isonHold());
		this.owner = new AdvertiserDTO(comp.getOwner());
	}

	public boolean isOnHold() {
		return onHold;
	}

	public void setOnHold(boolean onHold) {
		this.onHold = onHold;
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
