package com.nekretnine.dto;

import com.nekretnine.models.Comment;

public class CommentDTO {

	private Long id;
	private String data;
	private AdvertisementDTO advertisementDTO;
	private long time;
	
	public CommentDTO() {}
	public CommentDTO(Comment comment) {
		this.id  = comment.getId();
		this.data  = comment.getData();
		this.advertisementDTO  = new AdvertisementDTO(comment.getAdvertisement());
		this.time  = comment.getTime();
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
	
	public AdvertisementDTO getAdvertisementDTO() {
		return advertisementDTO;
	}
	public void setAdvertisementDTO(AdvertisementDTO advertisementDTO) {
		this.advertisementDTO = advertisementDTO;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
