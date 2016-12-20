package com.nekretnine.dto;

import java.util.Date;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Advertisement.State;

public class AdvertisementDTO {


	
	private Long id;
	private Date publicationDate;
	private Date expiryDate;
	private State state;
	private AdvertiserDTO advertiser;
	private EstateDTO estate;
	
	
	public AdvertisementDTO() {
		super();
	}

	public AdvertisementDTO(Long id, Date publicationDate, Date expiryDate,
			State state, AdvertiserDTO advertiser, EstateDTO estate) {
		super();
		this.id = id;
		this.publicationDate = publicationDate;
		this.expiryDate = expiryDate;
		this.state = state;
		this.advertiser = advertiser;
		this.estate = estate;

	}
	
	public AdvertisementDTO(Advertisement advertisement) {
		this.id = advertisement.getId();
		this.publicationDate = advertisement.getPublicationDate();
		this.expiryDate = advertisement.getExpiryDate();
		this.state = advertisement.getState();
		this.advertiser =new AdvertiserDTO(advertisement.getAdvertiser());
		this.estate = new EstateDTO(advertisement.getEstate());
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

	public EstateDTO getEstate() {
		return estate;
	}

	public void setEstate(EstateDTO estate) {
		this.estate = estate;
	}

	@Override
	public String toString() {
		return "AdvertisementDTO [id=" + id + ", publicationDate=" + publicationDate + ", expiryDate=" + expiryDate
				+ ", state=" + state + ", advertiser=" + advertiser + ", estate=" + estate + "]";
	}
	
	
	
}
