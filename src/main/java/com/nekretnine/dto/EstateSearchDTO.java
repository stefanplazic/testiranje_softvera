package com.nekretnine.dto;

import java.util.Set;

public class EstateSearchDTO {

	
	private	long id; 
	private String name;
	private double minPrice;
	private double maxPrice;
	private double minArea;
	private double maxArea;
	private String address;
	private String city;
	private String cityPart;
	private String technicalEquipment;
	private String heatingSystem;
	private AdvertiserDTO owner;
	private Set<ImageDTO> images;
	
	public EstateSearchDTO() {
		super();
	}
	
	public EstateSearchDTO(long id, String name, double minPrice, double maxPrice, double minArea, double maxArea,
			String address, String city, String cityPart, String technicalEquipment, String heatingSystem,
			AdvertiserDTO owner, Set<ImageDTO> images) {
		super();
		this.id = id;
		this.name = name;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.minArea = minArea;
		this.maxArea = maxArea;
		this.address = address;
		this.city = city;
		this.cityPart = cityPart;
		this.technicalEquipment = technicalEquipment;
		this.heatingSystem = heatingSystem;
		this.owner = owner;
		this.images = images;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public double getMinArea() {
		return minArea;
	}
	public void setMinArea(double minArea) {
		this.minArea = minArea;
	}
	public double getMaxArea() {
		return maxArea;
	}
	public void setMaxArea(double maxArea) {
		this.maxArea = maxArea;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityPart() {
		return cityPart;
	}
	public void setCityPart(String cityPart) {
		this.cityPart = cityPart;
	}
	public String getTechnicalEquipment() {
		return technicalEquipment;
	}
	public void setTechnicalEquipment(String technicalEquipment) {
		this.technicalEquipment = technicalEquipment;
	}
	public String getHeatingSystem() {
		return heatingSystem;
	}
	public void setHeatingSystem(String heatingSystem) {
		this.heatingSystem = heatingSystem;
	}
	public AdvertiserDTO getOwner() {
		return owner;
	}
	public void setOwner(AdvertiserDTO owner) {
		this.owner = owner;
	}
	public Set<ImageDTO> getImages() {
		return images;
	}
	public void setImages(Set<ImageDTO> images) {
		this.images = images;
	}
	
}
