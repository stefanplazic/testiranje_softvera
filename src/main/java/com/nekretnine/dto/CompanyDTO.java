package com.nekretnine.dto;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Company;

public class CompanyDTO {

	private long id;
	private String name;
	private String address;
	private Advertiser owner;
	private boolean on_hold;
	
	public CompanyDTO(){}
	
	public CompanyDTO(long id, String name, String address,
			boolean on_hold) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.on_hold = on_hold;
	}

	public CompanyDTO(Company comp){
		this(comp.getId(), comp.getName(), comp.getAddress(),
				comp.isOn_hold() );
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

	public Advertiser getOwner() {
		return owner;
	}

	public void setOwner(Advertiser owner) {
		this.owner = owner;
	}

	public boolean isOn_hold() {
		return on_hold;
	}

	public void setOn_hold(boolean on_hold) {
		this.on_hold = on_hold;
	}
	
	
	
}
