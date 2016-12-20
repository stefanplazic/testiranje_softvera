package com.nekretnine.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Favourites {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer")
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "estate")
	private Estate estate;

	public Favourites(Long id, Customer customerFav, Estate estateFav) {
		super();
		this.id = id;
		this.customer = customerFav;
		this.estate = estateFav;
	}
	
	public Favourites(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customerFav) {
		this.customer = customerFav;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estateFav) {
		this.estate = estateFav;
	}

	
	
	
}
