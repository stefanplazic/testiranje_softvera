package com.nekretnine.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.nekretnine.models.Advertisement;
import com.nekretnine.models.Comment;
import com.nekretnine.models.Report;

public class AdvertisementDTO {

	public enum State {
		OPEN, EXPIRED, REPORTED, REMOVED, SOLD
	}
	
	private Long id;
	private Date publicationDate;
	private Date expiryDate;
	private Date lastUpdate;
	private String contactInfo;
	private State state;
	private Set<CommentDTO> comments = new HashSet<CommentDTO>();
	private AdvertiserDTO advertiser;
	private EstateDTO estate;
	private Set<ReportDTO> reports = new HashSet<ReportDTO>();
	
	public AdvertisementDTO() {}

	public AdvertisementDTO(Long id, Date publicationDate, Date expiryDate, Date lastUpdate,
			String contactInfo, State state, Set<CommentDTO> comments, AdvertiserDTO advertiser, EstateDTO estate,
			Set<ReportDTO> reports) {
		super();
		this.id = id;
		this.publicationDate = publicationDate;
		this.expiryDate = expiryDate;
		this.lastUpdate = lastUpdate;
		this.contactInfo = contactInfo;
		this.state = state;
		this.comments = comments;
		this.advertiser = advertiser;
		this.estate = estate;
		this.reports = reports;
	}
	
	public AdvertisementDTO(Advertisement advertisement) {
		this.id = advertisement.getId();
		this.publicationDate = advertisement.getPublicationDate();
		this.expiryDate = advertisement.getExpiryDate();
		this.lastUpdate = advertisement.getLastUpdate();
		this.contactInfo = advertisement.getContactInfo();
		setState(advertisement.getState());
		
		for(Comment comm : advertisement.getComments()) {
			this.comments.add(new CommentDTO(comm));
		}
		this.advertiser = new AdvertiserDTO(advertisement.getAdvertiser());
		this.estate = new EstateDTO(advertisement.getEstate());
		for(Report rep : advertisement.getReports()) {
			this.reports.add(new ReportDTO(rep));
		}
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

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public State getState() {
		return state;
	}
	
	//obican setter ce da se pokrlja sa dole napisanim zbog nekog razloga
	
	public void setState(Advertisement.State state) {
		if(state == Advertisement.State.OPEN) {
			this.state = State.OPEN;
		}
		else if (state == Advertisement.State.EXPIRED) {
			this.state = State.EXPIRED;
		}
		else if (state == Advertisement.State.REPORTED) {
			this.state = State.REPORTED;
		}
		else if (state == Advertisement.State.REMOVED) {
			this.state = State.REMOVED;
		}
		else {
			this.state = State.SOLD;
		}
	}

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
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

	public Set<ReportDTO> getReports() {
		return reports;
	}

	public void setReports(Set<ReportDTO> reports) {
		this.reports = reports;
	}

}
