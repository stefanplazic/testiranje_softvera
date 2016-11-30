package com.nekretnine.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Advertiser extends User{

	@ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
	private Company company;
	
	

	public Advertiser() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Advertiser(String first_name, String last_name, String email, String username, String password,Company company) {
		super(first_name, last_name, email, username, password);
		this.company = company;
	}



	public Company getCompany() {
		return company;
	}



	public void setCompany(Company company) {
		this.company = company;
	}

	
}
