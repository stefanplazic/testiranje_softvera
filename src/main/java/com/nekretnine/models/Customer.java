package com.nekretnine.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Customer extends User {

	@Column(nullable = false)
	@OneToMany(mappedBy="customer", fetch = FetchType.LAZY)
	private Set<Rate> rates  = new HashSet<Rate>();

	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private Set<Comment> comments = new HashSet<Comment>();
	
	public Customer() {
		super();
	}

	public Customer(Set<Rate> rates, Set<Comment> comments) {
		super();
		this.rates = rates;
		this.comments = comments;
	}

	public Set<Rate> getRates() {
		return rates;
	}

	public void setRates(Set<Rate> rates) {
		this.rates = rates;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
}
