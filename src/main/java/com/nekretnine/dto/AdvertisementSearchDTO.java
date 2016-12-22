package com.nekretnine.dto;

import java.util.Date;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;

public class AdvertisementSearchDTO {

	private Long id;
	private Date publicationDate;
	private Date expiryDate;
	private State state;
	private AdvertiserDTO advertiser;
	private EstateSearchDTO estate;
	
	public AdvertisementSearchDTO() {
		super();
	}

	public AdvertisementSearchDTO(Long id, Date publicationDate, Date expiryDate, State state, AdvertiserDTO advertiser,
			EstateSearchDTO estate) {
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

	public AdvertiserDTO getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(AdvertiserDTO advertiser) {
		this.advertiser = advertiser;
	}

	public EstateSearchDTO getEstate() {
		return estate;
	}

	public void setEstate(EstateSearchDTO estate) {
		this.estate = estate;
	}
	
}
