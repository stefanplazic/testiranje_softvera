package com.nekretnine.dto;

import com.nekretnine.models.Report;

public class ReportDTO {

	public enum State {
		OPEN, CLOSED
	}
	
	private Long id;
	private UserDTO user;
	private AdvertisementDTO advertisement;
	private State state;
	
	public ReportDTO() {
		super();
	}

	public ReportDTO(Long id, UserDTO user, AdvertisementDTO advertisement, State state) {
		super();
		this.id = id;
		this.user = user;
		this.advertisement = advertisement;
		this.state = state;
	}
	
	public ReportDTO(Report rep) {
		this.id = rep.getId();
		this.user = new UserDTO(rep.getUser());
		this.advertisement = new AdvertisementDTO(rep.getAdvertisement());
		if(rep.getState() == Report.State.OPEN) {
			this.state = State.OPEN;
		}
		else {
			this.state = State.CLOSED;
		}
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
}
