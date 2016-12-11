package com.nekretnine.dto;

import com.nekretnine.models.Comment;

public class CommentDTO {

	private Long id;
	private String data;
	private CustomerDTO user;
	private AdvertisementDTO advertisement;
	private long time;
	
	public CommentDTO() {
		super();
	}

	public CommentDTO(Long id, String data, CustomerDTO user, AdvertisementDTO advertisement, long time) {
		super();
		this.id = id;
		this.data = data;
		this.user = user;
		this.advertisement = advertisement;
		this.time = time;
	}
	
	public CommentDTO(Comment comment) {
		this.id = comment.getId();
		this.data = comment.getData();
		this.user = new CustomerDTO(comment.getUser());
		this.advertisement = new AdvertisementDTO(comment.getAdvertisement());
		this.time = comment.getTime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public CustomerDTO getUser() {
		return user;
	}

	public void setUser(CustomerDTO user) {
		this.user = user;
	}

	public AdvertisementDTO getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(AdvertisementDTO advertisement) {
		this.advertisement = advertisement;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
