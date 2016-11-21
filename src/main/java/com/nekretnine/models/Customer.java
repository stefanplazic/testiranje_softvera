package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Customer extends User{

	@Column(nullable = false)
	@OneToMany(mappedBy="customer", fetch = FetchType.LAZY)
	private Set<Rate> rates  = new HashSet<Rate>();

	public Customer(Set<Rate> rates) {
		super();
		this.rates = rates;
	}
	
	public Customer() {
		super();
	}

	public Set<Rate> getRates() {
		return rates;
	}

	public void setRates(Set<Rate> rates) {
		this.rates = rates;
	}
	
}
