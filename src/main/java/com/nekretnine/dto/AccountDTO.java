package com.nekretnine.dto;

import com.nekretnine.models.Account;

public class AccountDTO {

	private Long id;
	private double amount;
	private String acountNumber;
	
	public AccountDTO() {}
	
	public AccountDTO(Account account) {
		this.id = account.getId();
		this.amount = account.getAmount();
		this.acountNumber = account.getAcountNumber();
	}
	
	public AccountDTO(Long id, double amount, String acountNumber) {
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
