package com.nekretnine.dto;

public class CustomerMessageDTO {

	private String message;
	private Long advertisementId;
	
	public CustomerMessageDTO(){}
	
	public CustomerMessageDTO(String message, Long advertisementId) {
		super();
		this.message = message;
		this.advertisementId = advertisementId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Long advertisementId) {
		this.advertisementId = advertisementId;
	}
	
	
}
