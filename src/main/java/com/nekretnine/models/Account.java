package com.nekretnine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private double amount;

	@Column(nullable = false, unique = true)
	private String acountNumber;

	public Account() {
	}

	public Account(Long id, double amount, String acountNumber) {
		super();
		this.id = id;
		this.amount = amount;
		this.acountNumber = acountNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getAcountNumber() {
		return acountNumber;
	}

	public void setAcountNumber(String acountNumber) {
		this.acountNumber = acountNumber;
	}

}
