package com.nekretnine.dto;

import java.util.Date;

import com.nekretnine.models.View;

public class ViewDTO {

	private UserDTO viewer;
	private AdvertisementDTO advert;
	private Date time;
	
	public ViewDTO() {
		super();
	}

	public ViewDTO(UserDTO viewer, AdvertisementDTO advert, Date time) {
		super();
		this.viewer = viewer;
		this.advert = advert;
		this.time = time;
	}
	
	public ViewDTO(View v) {
		this.viewer = new UserDTO(v.getViewer());
		this.advert = new AdvertisementDTO(v.getAdvert());
		this.time = v.getTime();
	}

	public UserDTO getViewer() {
		return viewer;
	}

	public void setViewer(UserDTO viewer) {
		this.viewer = viewer;
	}

	public AdvertisementDTO getAdvert() {
		return advert;
	}

	public void setAdvert(AdvertisementDTO advert) {
		this.advert = advert;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}
