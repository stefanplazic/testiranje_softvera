package com.nekretnine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.nekretnine.dto.CommentDTO;

@Entity
public class Comment {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(nullable = true)
	private String data;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user")
	private Customer user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="advertisement")
	private Advertisement advertisement;
	
	@Column(nullable = true)
	private long time;
	
	public Comment() {}

	public Comment(Long id, String data, Customer user, Advertisement advertisement, long time) {
		super();
		this.id = id;
		this.data = data;
		this.user = user;
		this.advertisement = advertisement;
		this.time = time;
	}
	
	public Comment(CommentDTO comdto) {
		this.id = comdto.getId();
		this.data = comdto.getData();
		this.user = new Customer(comdto.getUser());
		this.advertisement = new Advertisement(comdto.getAdvertisement());
		this.time = comdto.getTime();
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

	public Customer getUser() {
		return user;
	}

	public void setUser(Customer user) {
		this.user = user;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
