package com.nekretnine.dto;

public class AdvertiserMessageDTO {

	private String message;
	private Long advertisementId;
	private Long toUserId;

	public AdvertiserMessageDTO() {
		super();
	}

	public AdvertiserMessageDTO(String message, Long advertisementId, Long toUserId) {
		super();
		this.message = message;
		this.advertisementId = advertisementId;
		this.toUserId = toUserId;
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

	public Long getToUserId() {
		return toUserId;
	}

	public void setToUserId(Long toUserId) {
		this.toUserId = toUserId;
	}
	
	
}
