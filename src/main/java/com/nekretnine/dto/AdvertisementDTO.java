package com.nekretnine.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nekretnine.models.Advertiser;
import com.nekretnine.models.Estate;
import com.nekretnine.models.Advertisement.State;

public class AdvertisementDTO {


	
	private Long id;
	private Date publicationDate;
	private Date expiryDate;
	private State state;
	private Advertiser advertiser;
	private Estate estate;
	
	
	public AdvertisementDTO() {
		super();
	}

	public AdvertisementDTO(Long id, Date publicationDate, Date expiryDate,
			State state, Advertiser advertiser, Estate estate) {
		super();
		this.id = id;
		this.publicationDate = publicationDate;
		this.expiryDate = expiryDate;
		this.state = state;
		this.advertiser = advertiser;
		this.estate = estate;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Advertiser getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(Advertiser advertiser) {
		this.advertiser = advertiser;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

	@Override
	public String toString() {
		return "AdvertisementDTO [id=" + id + ", publicationDate=" + publicationDate + ", expiryDate=" + expiryDate
				+ ", state=" + state + ", advertiser=" + advertiser + ", estate=" + estate + "]";
	}
	
	
	
}
