package com.nekretnine.dto;

import java.util.Date;

import com.nekretnine.models.Notification;

public class NotificationDTO {

	private Long id;
	private String text;
	private String nType;
	private UserDTO fromUser;
	private Date made;
	private boolean seen;
	private AdvertisementDTO advertisement;
	
	public NotificationDTO(){
		super();
	}

	public NotificationDTO(Long id, String text, String nType, UserDTO fromUser,
			Date made, boolean seen) {
		super();
		this.id = id;
		this.text = text;
		this.nType = nType;
		this.fromUser = fromUser;
		this.made = made;
		this.seen = seen;
	}
	
	public NotificationDTO(Notification notification) {
		super();
		this.id = notification.getId();
		this.text = notification.getText();
		this.nType = notification.getnType();
		this.fromUser = new UserDTO(notification.getFromUser());
		this.made = notification.getMade();
		this.seen = notification.isSeen();
		
		if("message".equals(this.nType))
			this.advertisement = new AdvertisementDTO(notification.getAdvertisement());
		
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getnType() {
		return nType;
	}

	public void setnType(String nType) {
		this.nType = nType;
	}

	public UserDTO getFromUser() {
		return fromUser;
	}

	public void setFromUser(UserDTO fromUser) {
		this.fromUser = fromUser;
	}

	public Date getMade() {
		return made;
	}

	public void setMade(Date made) {
		this.made = made;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public AdvertisementDTO getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(AdvertisementDTO advertisement) {
		this.advertisement = advertisement;
	}
	
	
	
	
}
