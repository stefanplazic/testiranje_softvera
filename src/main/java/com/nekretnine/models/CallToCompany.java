package com.nekretnine.models;



import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CallToCompany {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private Date DateOfCall;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "fromadvrt")
	private Advertiser fromadvrt;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "toadvrt")
	private Advertiser toadvrt;
	
	public CallToCompany() {}

	public CallToCompany(Long id, Date dateOfCall, Advertiser fromAdvertiser, Advertiser toAdvertiser) {
		super();
		this.id = id;
		DateOfCall = dateOfCall;
		this.fromadvrt = fromAdvertiser;
		this.toadvrt = toAdvertiser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfCall() {
		return DateOfCall;
	}

	public void setDateOfCall(Date dateOfCall) {
		DateOfCall = dateOfCall;
	}

	public Advertiser getFromAdvertiser() {
		return fromadvrt;
	}

	public void setFromAdvertiser(Advertiser fromAdvertiser) {
		this.fromadvrt = fromAdvertiser;
	}

	public Advertiser getToAdvertiser() {
		return toadvrt;
	}

	public void setToAdvertiser(Advertiser toAdvertiser) {
		this.toadvrt = toAdvertiser;
	}
	
}
