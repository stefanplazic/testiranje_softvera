package com.nekretnine.dto;

import com.nekretnine.models.Report;
import com.nekretnine.models.User;

public class ReportDTO {

	private Long id;
	private UserDTO user;
	private AdvertisementDTO advertisement;
	private String message;
	private String status;
	private boolean onHold;
	
	public ReportDTO(Long id, UserDTO user, AdvertisementDTO advertisement, String message, String status, boolean onHold) {
		super();
		this.id = id;
		this.user = user;
		this.advertisement = advertisement;
		this.message = message;
		this.status = status;
		this.onHold = onHold;
	}

	public ReportDTO(Report r) {
		this.id = r.getId();
		this.user = new UserDTO(r.getUser());
		this.advertisement = new AdvertisementDTO(r.getAdvertisement());
		this.message = r.getMessage();
		this.status = r.getStatus();
		this.onHold = r.isOnHold();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public AdvertisementDTO getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(AdvertisementDTO advertisement) {
		this.advertisement = advertisement;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void getStatus(String status) {
		this.status = status;
	}

	public boolean isOnHold() {
		return onHold;
	}

	public void setOnHold(boolean onHold) {
		this.onHold = onHold;
	}
	
}
