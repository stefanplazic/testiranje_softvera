package com.nekretnine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nekretnine.dto.ReportDTO;

@Entity
public class Report {

	public enum State {
		OPEN, CLOSED
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	@ManyToOne
	@JoinColumn(name = "advertisement")
	private Advertisement advertisement;
	
	@Column
	private State state;

	public Report() {
		super();
	}

	public Report(Long id, User user, Advertisement advertisement, State state) {
		super();
		this.id = id;
		this.user = user;
		this.advertisement = advertisement;
		this.state = state;
	}
	
	public Report(ReportDTO repdto) {
		this.id = repdto.getId();
		this.user = new User(repdto.getUser());
		this.advertisement = new Advertisement(repdto.getAdvertisement());
		setState(repdto.getState());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public State getState() {
		return state;
	}

	public void setState(Object state) {
		if(state instanceof Report.State) {
			this.state = (Report.State)state;
		}
		else if (state instanceof ReportDTO.State) {
			if(state == ReportDTO.State.OPEN) {
				this.state = State.OPEN;
			}
			else {
				this.state = State.CLOSED;
			}
			
		}
	}	
	
}
