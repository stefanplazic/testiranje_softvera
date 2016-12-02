package com.nekretnine.dto;

import com.nekretnine.models.Location;

public class LocationDTO {

	private Long id;
	private String name;
	private double latitude;
	private double longitude;
	
	public LocationDTO() {}
	
	public LocationDTO(Location location) {
		this.id = location.getId();
		this.name = location.getName();
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	public LocationDTO(Long id, String name, double latitude, double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
